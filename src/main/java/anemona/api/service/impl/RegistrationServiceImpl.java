package anemona.api.service.impl;

import anemona.api.model.User;
import anemona.api.repository.jpa.RegistrationRepository;
import anemona.api.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    public RegistrationServiceImpl(RegistrationRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    @Override
    public User saveUser(User user) {
        return registrationRepository.save(user);
    }

    @Override
    public User fetchUserByEmail(String emailId) {
        return registrationRepository.findByEmailId(emailId);
    }

    @Override
    public User fetchUserByEmailAndPassword(String email, String password) {
        return registrationRepository.findByEmailIdAndPassword(email,password);
    }

    @Override
    public User fetchUserById(int userId) {
        return registrationRepository.findById(userId);
    }

}
