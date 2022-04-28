package anemona.api.repository.jpa;

import anemona.api.model.FavoritesCart;
import anemona.api.model.Product;
import anemona.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoritesCartRepository extends JpaRepository<FavoritesCart, Long> {
    Optional<FavoritesCart> findByUser(User user);
}
