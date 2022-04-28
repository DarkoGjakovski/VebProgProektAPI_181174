package anemona.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Category {

    @Id
    private Long id;
    private String name;

    @ManyToMany
    private List<Product> products;

    public Category() {
    }

    public Category(String Name) {
        this.name = Name;
        this.products = new ArrayList<>();
    }

}
