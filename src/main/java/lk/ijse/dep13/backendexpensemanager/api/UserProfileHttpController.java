package lk.ijse.dep13.backendexpensemanager.api;

import jakarta.servlet.http.HttpServletRequest;
import lk.ijse.dep13.backendexpensemanager.dto.UserRegisterDTO;
import lk.ijse.dep13.backendexpensemanager.entity.User;
import lk.ijse.dep13.backendexpensemanager.service.ProfileActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserProfileHttpController {

    @Autowired
    ProfileActivityService profileActivityService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> signup(@RequestBody UserRegisterDTO userRegisterDTO, HttpServletRequest request) {
        return profileActivityService.createAccount(userRegisterDTO);
    }

    @GetMapping("/me")
    public String getInfoUser(){
        return "getInfoUser";
    }

    @PutMapping("/me")
    public String updateInfoUser(){
        return "updateInfoUser";
    }

    @DeleteMapping("/me")
    public String deleteUser(){
        return "deleteUser";
    }
}
