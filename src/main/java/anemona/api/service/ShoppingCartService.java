package anemona.api.service;

import anemona.api.model.FavoritesCart;
import anemona.api.model.Product;
import anemona.api.model.ShoppingCart;
import anemona.api.model.ShoppingCartProduct;

import java.util.List;

public interface ShoppingCartService {
    List<ShoppingCartProduct> listAllProductsInShoppingCart(Long cartId);
    ShoppingCart getActiveShoppingCart(String username);
    ShoppingCart addProductToShoppingCart(String username, Long productId);
    ShoppingCart removeProductFromShoppingCart(String username, Long id);
}