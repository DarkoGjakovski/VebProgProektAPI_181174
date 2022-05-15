package anemona.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    private User user;

    private Integer ammount;

    private Boolean delivery;

    private Date createdAt;

    @ManyToMany
    public List<ShoppingCartProduct> shoppingCartProducts;

    public Transaction(){

    }

    public Transaction(User User, Integer Ammount, Boolean Delivery, Date CreatedAt, List<ShoppingCartProduct> ShoppingCartProducts){
        this.user = User;
        this.ammount = Ammount;
        this.delivery = Delivery;
        this.createdAt = CreatedAt;
        this.shoppingCartProducts = ShoppingCartProducts;
    }
}
