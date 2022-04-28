package anemona.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class FavoritesCart {

    @Id
    private Long favoritesCartId;

    @OneToMany
    private List<Product> products;

    @OneToOne
    private User user;

    public FavoritesCart(){

    }

    public FavoritesCart(List<Product> Products, User User){
        this.products = Products;
        this.user = User;
    }
}
