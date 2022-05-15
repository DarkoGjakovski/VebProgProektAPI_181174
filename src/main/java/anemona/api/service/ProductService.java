package anemona.api.service;

import anemona.api.model.Category;
import anemona.api.model.Product;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    List<Product> findByCategory(String category);

    Optional<Product> save(Product product);

    Optional<Product> edit(Long id, Product product);

    void deleteById(Long id);
}
