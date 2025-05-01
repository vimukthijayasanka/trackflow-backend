package lk.ijse.dep13.backendexpensemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUploadDTO implements Serializable {
    private MultipartFile profilePic;
    private String previousProfilePicUrl;
}
