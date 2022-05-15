package anemona.api.repository.jpa;

import anemona.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<User, Integer> {
    User findByEmailId(String emailId);
    User findById(int id);
    User findByEmailIdAndPassword(String email, String password);
}
