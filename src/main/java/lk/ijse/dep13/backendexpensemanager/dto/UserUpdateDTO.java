package lk.ijse.dep13.backendexpensemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDTO {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dob;
    private String password;
    private String profilePicUrl;
}
