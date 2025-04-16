package lk.ijse.dep13.backendexpensemanager.service;

import lk.ijse.dep13.backendexpensemanager.entity.AuditLog;
import lk.ijse.dep13.backendexpensemanager.enums.AuditAction;
import lk.ijse.dep13.backendexpensemanager.enums.AuditLogCategory;
import lk.ijse.dep13.backendexpensemanager.repository.AuditLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepo auditLogRepo;

    /**
     * Logs an action performed in the system.
     *
     * @param userName  The user who performed the action
     * @param action    The type of action (e.g., CREATE, DELETE)
     * @param category  The domain of the action (e.g., TRANSACTION, USER_PROFILE)
     * @param targetId  The ID or identifier of the target record
     * @param details   A descriptive log message (optional)
     */
    public void log(String userName,
                    AuditAction action,
                    AuditLogCategory category,
                    String targetId,
                    String details) {

        AuditLog log = new AuditLog();
        log.setUserName(userName);
        log.setAction(action);
        log.setCategory(category);
        log.setTargetId(targetId);
        log.setTimestamp(LocalDateTime.now());
        log.setDetails(details);

        auditLogRepo.save(log);
    }
}
