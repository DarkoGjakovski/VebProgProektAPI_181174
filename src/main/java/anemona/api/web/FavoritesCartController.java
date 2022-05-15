package anemona.api.web;

import anemona.api.model.FavoritesCart;
import anemona.api.model.Product;
import anemona.api.model.ShoppingCartProduct;
import anemona.api.service.FavoritesCartService;
import anemona.api.service.ProductService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/favorites")
public class FavoritesCartController {

    private final ProductService productService;
    private final FavoritesCartService favoritesCartService;

    public FavoritesCartController(ProductService productService, FavoritesCartService favoritesCartService) {
        this.productService = productService;
        this.favoritesCartService = favoritesCartService;
    }

    @GetMapping
    private ResponseEntity<List<Product>> findAll(@RequestParam("userId") int userId) {
        return ResponseEntity.ok().body(this.favoritesCartService.listAllProductsInFavoritesCart(this.favoritesCartService.getActiveFavoritesCart(userId).getFavoritesCartId()));
    }

    @PostMapping("/add")
    public ResponseEntity<ShoppingCartProduct> save(@RequestBody ObjectNode obj) {
        int userId = obj.get("userId").asInt();
        long productId =  obj.get("productId").asLong();
        this.favoritesCartService.addProductToFavoritesCart(userId,productId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<ShoppingCartProduct> remove(@RequestBody ObjectNode obj) {
        int userId = obj.get("userId").asInt();
        long productId =  obj.get("productId").asLong();
        this.favoritesCartService.removeProductFromFavoritesCart(userId,productId);
        return ResponseEntity.ok().build();
    }

}
