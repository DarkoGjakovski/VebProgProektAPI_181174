package anemona.api.web;

import anemona.api.model.FavoritesCart;
import anemona.api.model.Product;
import anemona.api.model.ShoppingCart;
import anemona.api.model.ShoppingCartProduct;
import anemona.api.service.FavoritesCartService;
import anemona.api.service.ProductService;
import anemona.api.service.ShoppingCartProductService;
import anemona.api.service.ShoppingCartService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {

    private final ProductService productService;
    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartProductService shoppingCartProductService;

    public ShoppingCartController(ProductService productService, ShoppingCartService shoppingCartService, ShoppingCartProductService shoppingCartProductService) {
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
        this.shoppingCartProductService = shoppingCartProductService;
    }

    @GetMapping
    private ResponseEntity<List<ShoppingCartProduct>> findAll(@RequestParam("userId") int userId) {
        return ResponseEntity.ok().body(this.shoppingCartService.listAllProductsInShoppingCart(this.shoppingCartService.getActiveShoppingCart(userId).getShoppingCartId()));
    }

    @PostMapping("/add")
    public ResponseEntity<ShoppingCartProduct> save(@RequestBody ObjectNode obj) {
        int userId = obj.get("userId").asInt();
        long productId =  obj.get("productId").asLong();
        this.shoppingCartService.addProductToShoppingCart(userId,productId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<ShoppingCartProduct> remove(@RequestBody ObjectNode obj) {
        int userId = obj.get("userId").asInt();
        long productId =  obj.get("productId").asLong();
        this.shoppingCartService.removeProductFromShoppingCart(userId,productId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<ShoppingCartProduct> delete(@RequestBody ObjectNode obj) {
        int userId = obj.get("userId").asInt();
        long productId =  obj.get("productId").asLong();
        this.shoppingCartService.deleteProductFromShoppingCart(userId,productId);
        return ResponseEntity.ok().build();
    }

}
