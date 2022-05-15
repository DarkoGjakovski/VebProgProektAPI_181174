package anemona.api.service.impl;

import anemona.api.model.FavoritesCart;
import anemona.api.model.Product;
import anemona.api.model.ShoppingCart;
import anemona.api.model.ShoppingCartProduct;
import anemona.api.model.exceptions.ProductAlreadyInShoppingCartException;
import anemona.api.model.exceptions.ProductNotFoundException;
import anemona.api.model.exceptions.ShoppingCartNotFoundException;
import anemona.api.model.exceptions.UserNotFoundException;
import anemona.api.repository.jpa.FavoritesCartRepository;
import anemona.api.service.FavoritesCartService;
import anemona.api.service.ProductService;
import anemona.api.service.RegistrationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoritesCartServiceImpl implements FavoritesCartService {

    private final FavoritesCartRepository favoritesCartRepository;
    private final ProductService productService;
    private final RegistrationService registrationService;

    public FavoritesCartServiceImpl(FavoritesCartRepository favoritesCartRepository,
                                   ProductService productService,
                                    RegistrationService registrationService) {
        this.favoritesCartRepository = favoritesCartRepository;
        this.productService = productService;
        this.registrationService = registrationService;
    }

    @Override
    public List<Product> listAllProductsInFavoritesCart(Long cartId) {
        if(!this.favoritesCartRepository.findById(cartId).isPresent())
            throw new ShoppingCartNotFoundException(cartId);
        return this.favoritesCartRepository.findById(cartId).get().getProducts();
    }

    @Override
    public FavoritesCart getActiveFavoritesCart(int userId) {
        return this.favoritesCartRepository.findByUser(this.registrationService.fetchUserById(userId));
    }

    @Override
    public FavoritesCart addProductToFavoritesCart(int userId, Long productId) {
        FavoritesCart favoritesCart = this.getActiveFavoritesCart(userId);
        if(favoritesCart == null){
            this.favoritesCartRepository.save(new FavoritesCart(this.registrationService.fetchUserById(userId)));
            favoritesCart = this.favoritesCartRepository.findByUser(this.registrationService.fetchUserById(userId));
        }

        Product product = this.productService.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        favoritesCart.addProduct(product);
        this.favoritesCartRepository.save(favoritesCart);
        return favoritesCart;
    }

    @Override
    public FavoritesCart removeProductFromFavoritesCart(int userId, Long productId) {
        FavoritesCart favoritesCart = this.getActiveFavoritesCart(userId);
        if(favoritesCart == null){
            this.favoritesCartRepository.save(new FavoritesCart(this.registrationService.fetchUserById(userId)));
            favoritesCart = this.favoritesCartRepository.findByUser(this.registrationService.fetchUserById(userId));
        }

        Product product = this.productService.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        favoritesCart.removeProduct(product);
        this.favoritesCartRepository.save(favoritesCart);
        return favoritesCart;
    }
}
