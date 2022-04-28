package anemona.api.repository.jpa;

import anemona.api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    void deleteByTitle(String title);
    Optional<Product> findByTitle(String name);
}
