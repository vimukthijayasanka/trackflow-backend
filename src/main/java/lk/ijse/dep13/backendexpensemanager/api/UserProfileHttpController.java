package lk.ijse.dep13.backendexpensemanager.api;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserProfileHttpController {

    @PostMapping(consumes = "application/json")
    public String signup(){
        return "signup";
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
