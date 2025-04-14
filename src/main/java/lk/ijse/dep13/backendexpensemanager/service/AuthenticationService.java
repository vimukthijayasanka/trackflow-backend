package lk.ijse.dep13.backendexpensemanager.service;

import lk.ijse.dep13.backendexpensemanager.dto.UserLoginDTO;
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

    public UserLoginDTO login(UserLoginDTO userLogin){
        User user = userRepo.findByUserName(userLogin.getUserName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));

        String hashedInputPassword = DigestUtils.sha256Hex(userLogin.getPassword());

        if(!user.getPassword().equals(hashedInputPassword)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        return userLogin;
    }
}
