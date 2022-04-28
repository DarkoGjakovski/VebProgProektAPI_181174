package anemona.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class ShoppingCart {

    @Id
    private Long shoppingCartId;

    @OneToMany
    private List<ShoppingCartProduct> shoppingCartProducts;

    @OneToOne
    private User user;

    public ShoppingCart(){

    }

    public ShoppingCart(List<ShoppingCartProduct> ShoppingCartProducts, User User){
        this.shoppingCartProducts = ShoppingCartProducts;
        this.user = User;
    }

    public void add(ShoppingCartProduct product){
        this.shoppingCartProducts.add(product);
    }
}
