package lk.ijse.dep13.backendexpensemanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lk.ijse.dep13.backendexpensemanager.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;
    private String password;
    private String profilePicUrl;


    public UserDTO(User user) {
        this.userName = user.getUserName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.dob = user.getDob();
        this.password = user.getPassword();
        this.profilePicUrl = user.getProfilePictureUrl();
    }
}
