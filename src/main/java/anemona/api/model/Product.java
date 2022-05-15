package anemona.api.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Integer price;

    private String image;

    private String description;

    @ManyToMany
    private List<Category> categories;

    public Product() {
    }

    public Product(String Title, Integer Price, String Image, String Description, List<Category> Category) {
        this.title = Title;
        this.price = Price;
        this.image = Image;
        this.description = Description;
        this.categories = Category;
    }

}
