package anemona.api.service;

import anemona.api.model.FavoritesCart;
import anemona.api.model.ShoppingCart;
import anemona.api.model.ShoppingCartProduct;

public interface ShoppingCartProductService {
    ShoppingCartProduct save(ShoppingCartProduct shoppingCartProduct);
    ShoppingCartProduct getShoppingCartProduct(Long id);
}
