package lk.ijse.dep13.backendexpensemanager.service;

import lk.ijse.dep13.backendexpensemanager.dto.UserRegisterDTO;
import lk.ijse.dep13.backendexpensemanager.entity.User;
import lk.ijse.dep13.backendexpensemanager.repository.UserRepo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProfileActivityService {

    @Autowired
    private UserRepo userRepo;

    public ResponseEntity<String> createAccount(UserRegisterDTO userRegisterDTO) {
        if (userRepo.existsByUserName(userRegisterDTO.getUserName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User name already exists");
        } else {
            User user = new User();
            user.setUserName(userRegisterDTO.getUserName());
            user.setPassword(DigestUtils.sha256Hex(userRegisterDTO.getPassword()));
            user.setFirstName(userRegisterDTO.getFirstName());
            user.setLastName(userRegisterDTO.getLastName());
            user.setEmail(userRegisterDTO.getEmail());
            user.setDob(userRegisterDTO.getDob());
            user.setProfilePictureUrl(userRegisterDTO.getProfilePicUrl());
            userRepo.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");
        }
    }
}
