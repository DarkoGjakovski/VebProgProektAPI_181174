package anemona.api.web;

import anemona.api.model.FavoritesCart;
import anemona.api.model.Product;
import anemona.api.model.ShoppingCart;
import anemona.api.model.ShoppingCartProduct;
import anemona.api.service.FavoritesCartService;
import anemona.api.service.ProductService;
import anemona.api.service.ShoppingCartProductService;
import anemona.api.service.ShoppingCartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
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
    private ResponseEntity<List<ShoppingCartProduct>> findAll() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        ShoppingCart sc = this.shoppingCartService.getActiveShoppingCart(username);
        System.out.println(sc);
        return ResponseEntity.ok().body(sc.getShoppingCartProducts());
    }

    @PutMapping("/add/{id}")
    public ResponseEntity<ShoppingCartProduct> save(@PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        ShoppingCart sc = this.shoppingCartService.getActiveShoppingCart(username);
        ShoppingCartProduct scp = this.shoppingCartProductService.getShoppingCartProduct(id);
        this.shoppingCartService.addProductToShoppingCart(username,scp.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<ShoppingCartProduct> remove(@PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        ShoppingCart sc = this.shoppingCartService.getActiveShoppingCart(username);
        ShoppingCartProduct scp = this.shoppingCartProductService.getShoppingCartProduct(id);
        this.shoppingCartService.removeProductFromShoppingCart(username,scp.getId());
        return ResponseEntity.ok().build();
    }

}
