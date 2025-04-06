package lk.ijse.dep13.backendexpensemanager.service;

import lk.ijse.dep13.backendexpensemanager.entity.User;
import lk.ijse.dep13.backendexpensemanager.repository.UserRepo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepo userRepo;

    public User login(String userName, String password){
        User user = userRepo.findByUserName(userName).orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));

//        String encryptedPassword = DigestUtils.sha256Hex(password);
//        if(!user.getPassword().equals(encryptedPassword)){
//            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
//        }
        return user;
    }
}
