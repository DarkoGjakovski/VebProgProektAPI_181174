package anemona.api.service.impl;

import anemona.api.model.Category;
import anemona.api.model.Product;
import anemona.api.model.exceptions.CategoryNotFoundException;
import anemona.api.model.exceptions.ProductNotFoundException;
import anemona.api.repository.jpa.CategoryRepository;
import anemona.api.repository.jpa.ProductRepository;
import anemona.api.service.ProductService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        return this.productRepository.findById(id);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return this.productRepository.findByTitle(name);
    }

    @Override
    @Transactional
    public Optional<Product> save(Product product) {

        for (Category c: product.getCategories()) {
            Category category = this.categoryRepository.findById(c.getId())
                    .orElseThrow(() -> new CategoryNotFoundException(c.getId()));
        }
        return Optional.of(this.productRepository.save(product));
    }

    @Override
    @Transactional
    public Optional<Product> edit(Long id, Product product) {
        Product findProduct = this.productRepository.findById(product.getId()).orElseThrow(() -> new ProductNotFoundException(id));

        findProduct.setTitle(product.getTitle());
        findProduct.setPrice(product.getPrice());
        findProduct.setDescription(product.getDescription());
        findProduct.setImage(product.getImage());

        for (Category c: product.getCategories()) {
            this.categoryRepository.findById(c.getId())
                    .orElseThrow(() -> new CategoryNotFoundException(c.getId()));
        }

        product.setCategories(product.getCategories());

        return Optional.of(this.productRepository.save(product));

    }

    @Override
    public void deleteById(Long id) {
        this.productRepository.deleteById(id);
    }
}
