package anemona.api.service.impl;

import anemona.api.model.Product;
import anemona.api.model.ShoppingCart;
import anemona.api.model.ShoppingCartProduct;
import anemona.api.model.exceptions.ProductAlreadyInShoppingCartException;
import anemona.api.model.exceptions.ProductNotFoundException;
import anemona.api.model.exceptions.ShoppingCartNotFoundException;
import anemona.api.model.exceptions.UserNotFoundException;
import anemona.api.repository.jpa.RegistrationRepository;
import anemona.api.repository.jpa.ShoppingCartProductRepository;
import anemona.api.repository.jpa.ShoppingCartRepository;
import anemona.api.service.ProductService;
import anemona.api.service.RegistrationService;
import anemona.api.service.ShoppingCartProductService;
import anemona.api.service.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;
    private final RegistrationService registrationService;
    private final ShoppingCartProductService shoppingCartProductService;
    private final ShoppingCartProductRepository shoppingCartProductRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   ProductService productService,
                                   RegistrationService registrationService,
                                   ShoppingCartProductService shoppingCartProductService,
                                   ShoppingCartProductRepository shoppingCartProductRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
        this.registrationService = registrationService;
        this.shoppingCartProductService = shoppingCartProductService;
        this.shoppingCartProductRepository = shoppingCartProductRepository;
    }

    @Override
    public List<ShoppingCartProduct> listAllProductsInShoppingCart(Long cartId) {
        if(!this.shoppingCartRepository.findById(cartId).isPresent())
            throw new ShoppingCartNotFoundException(cartId);
        return this.shoppingCartRepository.findById(cartId).get().getShoppingCartProducts();
    }

    @Override
    public ShoppingCart getActiveShoppingCart(int userId) {
        return this.shoppingCartRepository.findByUser(this.registrationService.fetchUserById(userId));
    }

    @Override
    public ShoppingCart addProductToShoppingCart(int userId, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        if(shoppingCart == null){
            this.shoppingCartRepository.save(new ShoppingCart(this.registrationService.fetchUserById(userId)));
            shoppingCart = this.shoppingCartRepository.findByUser(this.registrationService.fetchUserById(userId));
        }

        Product product = this.productService.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        if(shoppingCart.getShoppingCartProducts()
                .stream().anyMatch(i -> i.getProduct().getId().equals(productId))){
            List<ShoppingCartProduct> products = shoppingCart.getShoppingCartProducts();
            for (ShoppingCartProduct scp: products) {
                if(scp.getProduct().getId().equals(productId)){
                    scp.setQuantity(scp.getQuantity()+1);
                    this.shoppingCartProductRepository.save(scp);
                }
                break;
            }
        }else {
            this.shoppingCartProductService.save(new ShoppingCartProduct(product,1));
            shoppingCart.addProduct(new ShoppingCartProduct(product, 1));
            this.shoppingCartRepository.save(shoppingCart);
        }
        return shoppingCart;
    }

    @Override
    public ShoppingCart removeProductFromShoppingCart(int userId, Long productId) {
        ShoppingCart shoppingCart = this.getActiveShoppingCart(userId);
        if(shoppingCart == null){
            this.shoppingCartRepository.save(new ShoppingCart(this.registrationService.fetchUserById(userId)));
            shoppingCart = this.shoppingCartRepository.findByUser(this.registrationService.fetchUserById(userId));
        }

        this.productService.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        assert shoppingCart != null;
        if(shoppingCart.getShoppingCartProducts()
                .stream().anyMatch(i -> i.getProduct().getId().equals(productId))){
            List<ShoppingCartProduct> products = shoppingCart.getShoppingCartProducts();
            for (ShoppingCartProduct scp: products) {
                if(scp.getProduct().getId().equals(productId)){
                    if(scp.getQuantity()==1){
                        products.remove(scp.getId().intValue());
                        this.shoppingCartProductRepository.delete(scp);
                        break;
                    }
                    scp.setQuantity(scp.getQuantity()-1);
                    this.shoppingCartProductRepository.save(scp);
                }
                break;
            }
        }
        return shoppingCart;
    }

    @Override
    public ShoppingCart deleteProductFromShoppingCart(int userId, Long id) {
        ShoppingCart sp = this.shoppingCartRepository.findByUser(this.registrationService.fetchUserById(userId));
        List<ShoppingCartProduct> products = sp.getShoppingCartProducts();
        ShoppingCartProduct rmvProd = products.stream().filter(i -> id.intValue() == id).findFirst().orElse(null);
        sp.removeProduct(rmvProd);
        this.shoppingCartRepository.save(sp);
        return sp;
    }
}
