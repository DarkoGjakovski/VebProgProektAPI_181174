package anemona.api.service;

import anemona.api.model.User;

public interface AuthService {
    User login(String username, String password);
}
