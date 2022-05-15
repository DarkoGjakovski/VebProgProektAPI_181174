package anemona.api.service;

import anemona.api.model.User;
import org.springframework.stereotype.Service;

public interface RegistrationService {
    User saveUser(User user);
    User fetchUserByEmail(String email);
    User fetchUserById(int userId);
    User fetchUserByEmailAndPassword(String email, String pasword);
}
