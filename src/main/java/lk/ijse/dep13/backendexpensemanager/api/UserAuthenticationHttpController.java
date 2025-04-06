package lk.ijse.dep13.backendexpensemanager.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;

@RestController
@RequestMapping("/auth")
public class UserAuthenticationHttpController {

    @Autowired
    private Connection connection;

    @PostMapping("/login")
    public String login(){
        return "admin";
    }
    @DeleteMapping("/logout")
    public String logout(){
        return "logout";
    }
}
