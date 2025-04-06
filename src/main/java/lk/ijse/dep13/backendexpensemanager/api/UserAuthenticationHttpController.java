package lk.ijse.dep13.backendexpensemanager.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserAuthenticationHttpController {

    @PostMapping("/login")
    public String login(){
        return "admin";
    }
    @DeleteMapping("/logout")
    public String logout(){
        return "logout";
    }
}
