package anemona.api.service.impl;

import anemona.api.model.ShoppingCartProduct;
import anemona.api.repository.jpa.ShoppingCartProductRepository;
import anemona.api.service.ShoppingCartProductService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartProductServiceImpl implements ShoppingCartProductService {

    private ShoppingCartProductRepository shoppingCartProductRepository;

    public ShoppingCartProductServiceImpl(ShoppingCartProductRepository shoppingCartProductRepository) {
        this.shoppingCartProductRepository = shoppingCartProductRepository;
    }

    @Override
    public ShoppingCartProduct getShoppingCartProduct(Long id) {
        return this.shoppingCartProductRepository.getById(id);
    }
}
