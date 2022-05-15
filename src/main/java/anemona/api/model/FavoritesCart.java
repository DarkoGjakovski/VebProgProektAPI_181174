package anemona.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class FavoritesCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long favoritesCartId;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Product> products;

    @OneToOne
    private User user;

    public FavoritesCart(){

    }

    public FavoritesCart(User User){
        this.products = new ArrayList<>();
        this.user = User;
    }

    public void addProduct(Product product){
        this.products.add(product);
    }

    public void removeProduct(Product product){
        this.products.remove(product);
    }
}
