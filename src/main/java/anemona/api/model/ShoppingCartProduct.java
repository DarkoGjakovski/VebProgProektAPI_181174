package anemona.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class ShoppingCartProduct {

    @Id
    private Long id;

    @OneToOne
    private Product product;

    @OneToMany
    private List<ShoppingCart> shoppingCartsPerProduct;

    private Integer quantity;

    @OneToMany
    private List<Transaction> transactionPerProduct;

    public ShoppingCartProduct(){

    }

    public ShoppingCartProduct(Product Product, Integer Quantity){
        this.product = Product;
        this.quantity = Quantity;
    }
}
