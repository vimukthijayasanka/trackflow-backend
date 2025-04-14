package lk.ijse.dep13.backendexpensemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDate dob;
    private String password;
    private String profilePicUrl;
}
