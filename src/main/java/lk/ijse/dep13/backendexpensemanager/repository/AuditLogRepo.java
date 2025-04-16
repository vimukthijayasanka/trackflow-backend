package lk.ijse.dep13.backendexpensemanager.repository;

import lk.ijse.dep13.backendexpensemanager.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepo extends JpaRepository<AuditLog, Long> {
}
