package anemona.api.web;

import anemona.api.model.User;
import anemona.api.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/registeruser")
    @CrossOrigin(origins = "http://localhost:4200")
    public User registerUser(@RequestBody User user) throws Exception {
        String tempEmailId = user.getEmailId();
        if(tempEmailId != null && !"".equals(tempEmailId)){
            User userobj = registrationService.fetchUserByEmail(user.getEmailId());
            if(userobj!=null){
                throw new Exception("User with email "+ tempEmailId + "already exists.");
            }
        }
        User userObj = null;
        userObj = this.registrationService.saveUser(user);
        return userObj;
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public User loginUser(@RequestBody User user) throws Exception {
        String tempEmailId = user.getEmailId();
        String tempPass = user.getPassword();
        User userObj = null;
        if(tempEmailId!=null && tempPass != null){
            userObj = registrationService.fetchUserByEmailAndPassword(user.getEmailId(),user.getPassword());
        }
        if(userObj == null){
            throw new Exception("Bad credentials");
        }
        return userObj;
    }
}
