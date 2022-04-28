package anemona.api.service;

import anemona.api.model.Role;
import anemona.api.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    User register(String name, String username,String email, String password);
    User loadUserByUsername(String username) throws UsernameNotFoundException;
}
