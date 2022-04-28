package anemona.api.service.impl;

import anemona.api.model.User;
import anemona.api.model.exceptions.InvalidArgumentsException;
import anemona.api.model.exceptions.InvalidUserCredentialsException;
import anemona.api.repository.jpa.UserRepository;
import anemona.api.service.AuthService;

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User login(String username, String password) {
        if (username==null || username.isEmpty() || password==null || password.isEmpty()) {
            throw new InvalidArgumentsException();
        }
        return userRepository.findByUsernameAndPassword(username,
                password).orElseThrow(InvalidUserCredentialsException::new);
    }
}
