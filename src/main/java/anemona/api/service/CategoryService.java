package anemona.api.service;



import anemona.api.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    Category create(String name);

    Category update(String name);

    void delete(String name);

    List<Category> listCategories();

    List<Category> searchCategories(String searchText);

}
