package lk.ijse.dep13.backendexpensemanager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {
    @Pattern(regexp = "^[a-zA-Z0-9_]{5,20}$", message = "Username should be alphanumeric and between 5 to 20 characters")
    private String userName;

    @Pattern(regexp = "^[A-Za-z]{2,30}$", message = "First name must only contain letters")
    private String firstName;

    @Pattern(regexp = "^[A-Za-z]{2,30}$", message = "Last name must only contain letters")
    private String lastName;

    @Pattern(regexp = "(?i)[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", message = "Invalid email address")
    private String email;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "Weak password")
    private String password;

    @Pattern(regexp = "^(http|https)://.*$", message = "Invalid profile picture URL")
    private String profilePicUrl;
}
