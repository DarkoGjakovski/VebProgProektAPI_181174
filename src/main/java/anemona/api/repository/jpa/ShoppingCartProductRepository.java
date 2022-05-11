package anemona.api.repository.jpa;

import anemona.api.model.Product;
import anemona.api.model.ShoppingCartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartProductRepository extends JpaRepository<ShoppingCartProduct, Long> {
    Optional<ShoppingCartProduct> findById(Long id);
}
