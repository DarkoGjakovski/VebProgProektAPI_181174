package anemona.api.service.impl;

import anemona.api.model.Product;
import anemona.api.model.ShoppingCart;
import anemona.api.model.ShoppingCartProduct;
import anemona.api.model.User;
import anemona.api.model.exceptions.ProductAlreadyInShoppingCartException;
import anemona.api.model.exceptions.ProductNotFoundException;
import anemona.api.model.exceptions.ShoppingCartNotFoundException;
import anemona.api.model.exceptions.UserNotFoundException;
import anemona.api.repository.jpa.ShoppingCartRepository;
import anemona.api.repository.jpa.UserRepository;
import anemona.api.service.ProductService;
import anemona.api.service.ShoppingCartProductService;
import anemona.api.service.ShoppingCartService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ProductService productService;
    private final ShoppingCartProductService shoppingCartProductService;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   UserRepository userRepository,
                                   ProductService productService,
                                   ShoppingCartProductService shoppingCartProductService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.userRepository = userRepository;
        this.productService = productService;
        this.shoppingCartProductService = shoppingCartProductService;
    }

    @Override
    public List<ShoppingCartProduct> listAllProductsInShoppingCart(Long cartId) {
        if(this.shoppingCartRepository.findById(cartId).isEmpty())
            throw new ShoppingCartNotFoundException(cartId);
        return this.shoppingCartRepository.findById(cartId).get().getShoppingCartProducts();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(String username) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return this.shoppingCartRepository
                .findByUser(user)
                .orElseGet(() -> {
                    ShoppingCart cart = new ShoppingCart(new ArrayList<>(),user);
                    return this.shoppingCartRepository.save(cart);
                });
    }

    @Override
    public ShoppingCart addProductToShoppingCart(String username, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(username);
        Product product = this.productService.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));
        if(shoppingCart.getShoppingCartProducts()
                .stream().anyMatch(i -> i.getProduct().getId().equals(productId))){
            shoppingCart.getShoppingCartProducts().stream().map(i -> {
                if(i.getProduct().getId().equals(productId)){
                    i.setQuantity(i.getQuantity() + 1);
                }
                return i;
            });
        }

        shoppingCart.add(new ShoppingCartProduct(product,1));
        return this.shoppingCartRepository.save(shoppingCart);
    }
}
