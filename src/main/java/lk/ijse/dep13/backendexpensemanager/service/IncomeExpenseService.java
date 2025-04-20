package lk.ijse.dep13.backendexpensemanager.service;

import lk.ijse.dep13.backendexpensemanager.dto.ApiResponse;
import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseInfoDTO;
import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseDTO;
import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseUpdateDTO;
import lk.ijse.dep13.backendexpensemanager.entity.IncomeExpense;
import lk.ijse.dep13.backendexpensemanager.enums.AuditAction;
import lk.ijse.dep13.backendexpensemanager.enums.AuditLogCategory;
import lk.ijse.dep13.backendexpensemanager.repository.IncomeExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class IncomeExpenseService {
    @Autowired
    private IncomeExpenseRepo incomeExpenseRepo;
    @Autowired
    private AuditLogService auditLogService;

    public ResponseEntity<ApiResponse> createIncomeExpense(String userName, IncomeExpenseDTO incomeExpenseDTO) {
        IncomeExpense incomeExpense = new IncomeExpense();
        incomeExpense.setUserName(userName);
        incomeExpense.setType(incomeExpenseDTO.getType());
        incomeExpense.setDescription(incomeExpenseDTO.getDescription());
        incomeExpense.setAmount(incomeExpenseDTO.getAmount());
        incomeExpense.setTransactionDate(incomeExpenseDTO.getTransactionDate()!=null?incomeExpenseDTO.getTransactionDate():LocalDate.now());
        incomeExpenseRepo.save(incomeExpense);

        // log creation
        auditLogService.log(
                userName,
                AuditAction.CREATE,
                AuditLogCategory.TRANSACTION,
                String.valueOf(incomeExpense.getId()),
                "Created a new " + incomeExpenseDTO.getType().toString() +
                        " record for " + incomeExpenseDTO.getAmount() + " LKR"
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Successfully created your %s record".formatted(incomeExpenseDTO.getType())));
    }

    public IncomeExpenseInfoDTO getIncomeExpense(Long id, String userName) {
        IncomeExpense incomeExpense = incomeExpenseRepo.findByIdAndUserName(id, userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found for this user"));

        // log read
        auditLogService.log(
            userName,
            AuditAction.READ,
            AuditLogCategory.TRANSACTION,
            String.valueOf(incomeExpense.getId()),
            "Read " + incomeExpense.getType().toString() + " record for " + incomeExpense.getAmount() + " LKR"
        );

        return new IncomeExpenseInfoDTO(
            id,
            incomeExpense.getUserName(),
            incomeExpense.getType(),
            incomeExpense.getDescription(),
            incomeExpense.getAmount(),
            incomeExpense.getTransactionDate(),
            incomeExpense.getCreatedAt(),
            incomeExpense.getUpdatedAt()
        );
    }

    public List<IncomeExpenseInfoDTO> getAllIncomeExpense(String userName) {
        List<IncomeExpense> incomeExpenses = incomeExpenseRepo.findIncomeExpenseByUserName(userName);

        //log read
        auditLogService.log(
                userName,
                AuditAction.READ,
                AuditLogCategory.TRANSACTION,
                "NULL",
                "Read " + incomeExpenses.size() + " records for " + userName
        );

        return incomeExpenses.stream().map(incomeExpense -> {
           IncomeExpenseInfoDTO dto = new IncomeExpenseInfoDTO();
           dto.setUserName(incomeExpense.getUserName());
           dto.setType(incomeExpense.getType());
           dto.setDescription(incomeExpense.getDescription());
           dto.setAmount(incomeExpense.getAmount());
           dto.setTransactionDate(incomeExpense.getTransactionDate());
           dto.setCreatedAt(incomeExpense.getCreatedAt());
           dto.setUpdatedAt(incomeExpense.getUpdatedAt());
           return dto;
        }).toList();
    }

    public IncomeExpenseInfoDTO updateIncomeExpense(Long id, IncomeExpenseUpdateDTO updateDTO) {
        IncomeExpense incomeExpense = incomeExpenseRepo.findByIdAndUserName(id, updateDTO.getUserName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found or access denied"));

        if (updateDTO.getType() != null) {
            incomeExpense.setType(updateDTO.getType());
        }
        if (updateDTO.getDescription() != null) {
            incomeExpense.setDescription(updateDTO.getDescription());
        }
        if (updateDTO.getAmount() != null) {
            incomeExpense.setAmount(updateDTO.getAmount());
        }
        if (updateDTO.getTransactionDate() != null) {
            incomeExpense.setTransactionDate(updateDTO.getTransactionDate());
        }

        incomeExpense = incomeExpenseRepo.save(incomeExpense);

        //log update
        auditLogService.log(
          incomeExpense.getUserName(),
          AuditAction.UPDATE,
          AuditLogCategory.TRANSACTION,
          String.valueOf(incomeExpense.getId()),
                "Update " + incomeExpense.getId() + " ID" + incomeExpense.getType().toString() +
                        " record"
        );

        return new IncomeExpenseInfoDTO(
                (long) incomeExpense.getId(),
                incomeExpense.getUserName(),
                incomeExpense.getType(),
                incomeExpense.getDescription(),
                incomeExpense.getAmount(),
                incomeExpense.getTransactionDate(),
                incomeExpense.getCreatedAt(),
                incomeExpense.getUpdatedAt()
        );
    }

    public ResponseEntity<?> deleteIncomeExpense(Long id, String userName) {
        IncomeExpense transaction = incomeExpenseRepo.findByIdAndUserName(id, userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found or unauthorized"));

        incomeExpenseRepo.delete(transaction);

        // log delete
        auditLogService.log(
          transaction.getUserName(),
          AuditAction.DELETE,
          AuditLogCategory.TRANSACTION,
          String.valueOf(transaction.getId()),
          "Delete the transaction from user - " + transaction.getUserName()
        );

        return ResponseEntity.noContent().build();
    }
}
