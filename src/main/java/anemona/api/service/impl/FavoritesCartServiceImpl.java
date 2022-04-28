package anemona.api.service.impl;

import anemona.api.model.FavoritesCart;
import anemona.api.model.Product;
import anemona.api.model.User;
import anemona.api.model.exceptions.ProductAlreadyInShoppingCartException;
import anemona.api.model.exceptions.ProductNotFoundException;
import anemona.api.model.exceptions.ShoppingCartNotFoundException;
import anemona.api.model.exceptions.UserNotFoundException;
import anemona.api.repository.jpa.FavoritesCartRepository;
import anemona.api.repository.jpa.ShoppingCartRepository;
import anemona.api.repository.jpa.UserRepository;
import anemona.api.service.FavoritesCartService;
import anemona.api.service.ProductService;
import anemona.api.service.ShoppingCartProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FavoritesCartServiceImpl implements FavoritesCartService {

    private final FavoritesCartRepository favoritesCartRepository;
    private final UserRepository userRepository;
    private final ProductService productService;

    public FavoritesCartServiceImpl(FavoritesCartRepository favoritesCartRepository,
                                   UserRepository userRepository,
                                   ProductService productService) {
        this.favoritesCartRepository = favoritesCartRepository;
        this.userRepository = userRepository;
        this.productService = productService;
    }

    @Override
    public List<Product> listAllProductsInFavoritesCart(Long cartId) {
        if(!this.favoritesCartRepository.findById(cartId).isPresent())
            throw new ShoppingCartNotFoundException(cartId);
        return this.favoritesCartRepository.findById(cartId).get().getProducts();
    }

    @Override
    public FavoritesCart getActiveFavoritesCart(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return this.favoritesCartRepository
                .findByUser(user)
                .orElseGet(() -> {
                    FavoritesCart cart = new FavoritesCart(new ArrayList<>(), user);
                    return this.favoritesCartRepository.save(cart);
                });

    }

    @Override
    public FavoritesCart addProductToFavoritesCart(String username, Long productId) {
        FavoritesCart favoritesCart = this.getActiveFavoritesCart(username);
        Product product = this.productService.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        if(favoritesCart.getProducts()
                .stream().anyMatch(i -> i.getId().equals(productId)))
            throw new ProductAlreadyInShoppingCartException(productId, username);
        favoritesCart.getProducts().add(product);
        return this.favoritesCartRepository.save(favoritesCart);
    }
}
