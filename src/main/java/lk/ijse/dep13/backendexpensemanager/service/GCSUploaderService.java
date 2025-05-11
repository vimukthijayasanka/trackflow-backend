package lk.ijse.dep13.backendexpensemanager.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.*;
import lk.ijse.dep13.backendexpensemanager.exception.AppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class GCSUploaderService {

    private final Storage storage;

    @Value("${gcp.bucket.name}")
    private String bucketName;

    public GCSUploaderService() throws IOException {
        String credentialsJson = System.getenv("GCP_CREDENTIALS_JSON");
        if (credentialsJson != null || credentialsJson.isBlank()) {
            throw new AppException("Failed to load GCP credential path", 500);
        }
        GoogleCredentials credentials = GoogleCredentials.fromStream(new ByteArrayInputStream(credentialsJson.getBytes()))
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
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
        try{
            // upload new image
            storage.create(blobInfo, file.getBytes());
            // Make it public
            storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
            return String.format("https://storage.googleapis.com/%s/%s", bucketName, objectName);
        }catch (IOException e){
            throw new AppException("Failed to create the profile pic image",e, 500);
        }

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
            throw new AppException("Failed to delete the image", e, 500);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) return "";
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
