package lk.ijse.dep13.backendexpensemanager.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lk.ijse.dep13.backendexpensemanager.dto.UserDTO;
import lk.ijse.dep13.backendexpensemanager.dto.UserRegisterDTO;
import lk.ijse.dep13.backendexpensemanager.dto.UserUpdateDTO;
import lk.ijse.dep13.backendexpensemanager.service.ProfileActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Validated
@CrossOrigin
public class UserProfileHttpController {

    @Autowired
    ProfileActivityService profileActivityService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> signup(@RequestBody @Valid UserRegisterDTO userRegisterDTO, HttpServletRequest request) {
        return profileActivityService.createAccount(userRegisterDTO);
    }

    @GetMapping("/me")
    public UserDTO getInfoUser(@SessionAttribute(value = "user")String userName) {
        return profileActivityService.getInfoUser(userName);
    }

    @PutMapping(value = "/me", consumes = "application/json")
    public ResponseEntity<String> updateInfoUser(@SessionAttribute(value = "user") String userName, @RequestBody UserUpdateDTO userUpdateDTO) {
        return profileActivityService.updateInfoUser(userName, userUpdateDTO);
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteUser(@SessionAttribute(value = "user") String userName){
        return profileActivityService.deleteUser(userName);
    }
}
