package anemona.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shoppingCartId;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<ShoppingCartProduct> shoppingCartProducts;

    @OneToOne
    private User user;

    public ShoppingCart(){

    }

    public ShoppingCart(User User){
        this.user = User;
        this.shoppingCartProducts = new ArrayList<>();
    }

    public void addProduct(ShoppingCartProduct product){
        this.shoppingCartProducts.add(product);
    }

    public void removeProduct(ShoppingCartProduct product) {this.shoppingCartProducts.remove(product);}

    public void remove(Long id) {this.shoppingCartProducts = this.shoppingCartProducts.stream().filter(i -> Objects.equals(i.getId(), id)).collect(Collectors.toList());}

    public List<ShoppingCartProduct> getShoppingCartProducts() {
        return shoppingCartProducts;
    }
}
