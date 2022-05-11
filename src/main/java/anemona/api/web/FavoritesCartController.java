package anemona.api.web;

import anemona.api.model.FavoritesCart;
import anemona.api.model.Product;
import anemona.api.service.FavoritesCartService;
import anemona.api.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/favorites")
public class FavoritesCartController {

    private final ProductService productService;
    private final FavoritesCartService favoritesCartService;

    public FavoritesCartController(ProductService productService, FavoritesCartService favoritesCartService) {
        this.productService = productService;
        this.favoritesCartService = favoritesCartService;
    }

    @GetMapping
    private ResponseEntity<List<Product>> findAll() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        FavoritesCart fc = this.favoritesCartService.getActiveFavoritesCart(username);
        return ResponseEntity.ok().body(fc.getProducts());
    }

    @PutMapping("/add/{id}")
    public ResponseEntity<Product> save(@PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        FavoritesCart fc = this.favoritesCartService.getActiveFavoritesCart(username);
        this.favoritesCartService.addProductToFavoritesCart(username,id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<Product> remove(@PathVariable Long id) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        FavoritesCart fc = this.favoritesCartService.getActiveFavoritesCart(username);
        this.favoritesCartService.removeProductFromFavoritesCart(username,id);
        return ResponseEntity.ok().build();
    }


}
