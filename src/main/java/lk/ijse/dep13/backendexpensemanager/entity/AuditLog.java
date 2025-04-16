package lk.ijse.dep13.backendexpensemanager.entity;

import jakarta.persistence.*;
import lk.ijse.dep13.backendexpensemanager.enums.AuditAction;
import lk.ijse.dep13.backendexpensemanager.enums.AuditLogCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    @Enumerated(EnumType.STRING)
    private AuditAction action;
    @Enumerated(EnumType.STRING)
    private AuditLogCategory category; // e.g., TRANSACTION, USER_PROFILE, AUTH
    private String targetId; // e.g., transaction id or username
    private LocalDateTime timestamp;
    private String details;

    public AuditLog(String userName, AuditAction action, AuditLogCategory category, String targetId, LocalDateTime timestamp, String details) {
        this.userName = userName;
        this.action = action;
        this.category = category;
        this.targetId = targetId;
        this.timestamp = timestamp;
        this.details = details;
    }
}
