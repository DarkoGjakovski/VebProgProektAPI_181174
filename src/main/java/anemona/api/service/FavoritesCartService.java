package anemona.api.service;

import anemona.api.model.FavoritesCart;
import anemona.api.model.Product;

import java.util.List;

public interface FavoritesCartService {
    List<Product> listAllProductsInFavoritesCart(Long cartId);
    FavoritesCart getActiveFavoritesCart(int userId);
    FavoritesCart addProductToFavoritesCart(int userId, Long productId);
    FavoritesCart removeProductFromFavoritesCart(int userId, Long productId);
}
