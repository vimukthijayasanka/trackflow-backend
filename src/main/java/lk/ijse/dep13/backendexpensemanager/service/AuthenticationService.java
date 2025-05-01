package lk.ijse.dep13.backendexpensemanager.service;

import jakarta.servlet.http.HttpSession;
import lk.ijse.dep13.backendexpensemanager.dto.UserLoginDTO;
import lk.ijse.dep13.backendexpensemanager.dto.UserLoginResponse;
import lk.ijse.dep13.backendexpensemanager.entity.User;
import lk.ijse.dep13.backendexpensemanager.enums.AuditAction;
import lk.ijse.dep13.backendexpensemanager.enums.AuditLogCategory;
import lk.ijse.dep13.backendexpensemanager.repository.UserRepo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuditLogService auditLogService;

    public String login(UserLoginDTO userLogin){
        User user = userRepo.findByUserName(userLogin.getUserName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password"));

        String hashedInputPassword = DigestUtils.sha256Hex(userLogin.getPassword());

        if(!user.getPassword().equals(hashedInputPassword)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        //log LOGIN
        auditLogService.log(
                user.getUserName(),
                AuditAction.LOGIN,
                AuditLogCategory.AUTH,
                "NULL",
                user.getUserName() + " logged in at " + LocalDateTime.now()
        );
        return userLogin.getUserName();
    }

    public void logout(String userName, HttpSession session) {
        if (session != null) {
            session.invalidate();

            // log LOGOUT
            auditLogService.log(
                    userName,
                    AuditAction.LOGOUT,
                    AuditLogCategory.AUTH,
                    "NULL",
                    userName + " logged out at " + LocalDateTime.now()
            );
        }
    }
}
