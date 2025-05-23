package lk.ijse.dep13.backendexpensemanager.service;

import lk.ijse.dep13.backendexpensemanager.dto.UserDTO;
import lk.ijse.dep13.backendexpensemanager.dto.UserRegisterDTO;
import lk.ijse.dep13.backendexpensemanager.dto.UserUpdateDTO;
import lk.ijse.dep13.backendexpensemanager.entity.User;
import lk.ijse.dep13.backendexpensemanager.enums.AuditAction;
import lk.ijse.dep13.backendexpensemanager.enums.AuditLogCategory;
import lk.ijse.dep13.backendexpensemanager.repository.UserRepo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProfileActivityService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuditLogService auditLogService;

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

            // log create
            auditLogService.log(
                    user.getUserName(),
                    AuditAction.CREATE,
                    AuditLogCategory.AUTH,
                    user.getUserName(),
                    "Create the user - " + user.getUserName()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");
        }
    }

    public UserDTO getInfoUser(String userName) {
        User user = userRepo.findById(userName).orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username"));
        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(user.getUserName());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setDob(user.getDob());
        userDTO.setProfilePicUrl(user.getProfilePictureUrl());

        //log read
        auditLogService.log(
                user.getUserName(),
                AuditAction.READ,
                AuditLogCategory.USER_PROFILE,
                "NULL",
                "Read the profile information of - " + user.getUserName()
        );

        return userDTO;
    }

    public ResponseEntity<String> updateInfoUser(String userName, UserUpdateDTO userUpdateDTO) {
        User user = userRepo.findById(userName).orElseThrow(()-> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username"));
        if (userUpdateDTO.getFirstName() != null) {
            user.setFirstName(userUpdateDTO.getFirstName());
        }
        if (userUpdateDTO.getLastName() != null) {
            user.setLastName(userUpdateDTO.getLastName());
        }
        if (userUpdateDTO.getEmail() != null) {
            user.setEmail(userUpdateDTO.getEmail());
        }
        if (userUpdateDTO.getDob() != null) {
            user.setDob(userUpdateDTO.getDob());
        }
        if (userUpdateDTO.getProfilePicUrl() != null) {
            user.setProfilePictureUrl(userUpdateDTO.getProfilePicUrl());
        }
        userRepo.save(user);

        // log update
        auditLogService.log(
                user.getUserName(),
                AuditAction.UPDATE,
                AuditLogCategory.USER_PROFILE,
                "NULL",
                "Update the profile information of - " + user.getUserName()
        );

        String msg = String.format("%s's profile updated successfully", user.getUserName());
        return ResponseEntity.ok(msg);
    }

    public ResponseEntity<String> deleteUser(String userName) {
        User user = userRepo.findById(userName).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username"));
        userRepo.delete(user);

        // log delete
        auditLogService.log(
                user.getUserName(),
                AuditAction.DELETE,
                AuditLogCategory.USER_PROFILE,
                "NULL",
                "Delete the profile information of - " + user.getUserName()
        );

        String msg = String.format("%s's profile deleted successfully", userName);
        return ResponseEntity.ok(msg);
    }
}
