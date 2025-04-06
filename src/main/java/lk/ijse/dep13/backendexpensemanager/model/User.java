package lk.ijse.dep13.backendexpensemanager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String dob;
    private String password;
    private String profilePictureUrl;
}
