package lk.ijse.dep13.backendexpensemanager.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lk.ijse.dep13.backendexpensemanager.dto.UserLoginDTO;
import lk.ijse.dep13.backendexpensemanager.dto.UserLoginResponse;
import lk.ijse.dep13.backendexpensemanager.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class UserAuthenticationHttpController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request) {
        String userName = authenticationService.login(userLoginDTO);
        request.getSession().setAttribute("user", userName); // created Session for User
        return new UserLoginResponse(HttpStatus.OK, "Login successful");
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String userName = (String) session.getAttribute("user");
            if (userName != null) {
                authenticationService.logout(userName, session);
            }
        }
    }
}
