package lk.ijse.dep13.backendexpensemanager.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auto.value.AutoAnnotation;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class GCSUploaderService {

    private final Storage storage;

    @Value("${gcp.bucket.name}")
    private String bucketName;

    public GCSUploaderService(@Value("${gcp.credentials.path}")String credentialsPath) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream(credentialsPath));
        this.storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }

    public String uploadFile(String userName, MultipartFile file, String previousImageUrl) throws IOException {

        // Delete previous image if exists
        if ( previousImageUrl != null && !previousImageUrl.isBlank()){
            deletePreviousImage(previousImageUrl);
        }

        // Generate unique file name
        String extension = getFileExtension(file.getOriginalFilename());
        String fileName = UUID.randomUUID() + "_" + userName + (extension.isEmpty() ? "" : "." + extension);
        String objectName = "profile-pictures/" + fileName;

        // Create blob info
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        // upload new image
        storage.create(blobInfo, file.getBytes());
        // Make it public
        storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
        return String.format("https://storage.googleapis.com/%s/%s", bucketName, objectName);
    }

    private void deletePreviousImage(String previousImageUrl) {
        try{
            // Extract the object name from the url
            String prefix = String.format("https://storage.googleapis.com/%s/", bucketName);
            if (previousImageUrl.startsWith(prefix)){
                String objectName = previousImageUrl.substring(prefix.length());
                BlobId blobId = BlobId.of(bucketName, objectName);
                storage.delete(blobId);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) return "";
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
