package lk.ijse.dep13.backendexpensemanager.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lk.ijse.dep13.backendexpensemanager.dto.UserDTO;
import lk.ijse.dep13.backendexpensemanager.dto.UserLoginDTO;
import lk.ijse.dep13.backendexpensemanager.entity.User;
import lk.ijse.dep13.backendexpensemanager.service.AuthenticationService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;

@RestController
@RequestMapping("/auth")
public class UserAuthenticationHttpController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginDTO login(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) {
        UserLoginDTO user = authenticationService.login(userLoginDTO);
        request.getSession().setAttribute("user", user.getUserName()); // created Session for User
        return user;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // checking session is available
        if (session != null) {
            session.invalidate();
        }
    }
}
