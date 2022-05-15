package anemona.api.service;

import anemona.api.model.FavoritesCart;
import anemona.api.model.Product;
import anemona.api.model.ShoppingCart;
import anemona.api.model.ShoppingCartProduct;

import java.util.List;

public interface ShoppingCartService {
    List<ShoppingCartProduct> listAllProductsInShoppingCart(Long cartId);
    ShoppingCart getActiveShoppingCart(int userid);
    ShoppingCart addProductToShoppingCart(int userId, Long productId);
    ShoppingCart removeProductFromShoppingCart(int userId, Long id);
    ShoppingCart deleteProductFromShoppingCart(int userId, Long id);
}