package lk.ijse.dep13.backendexpensemanager.service;

import lk.ijse.dep13.backendexpensemanager.dto.ApiResponse;
import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseInfoDTO;
import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseDTO;
import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseUpdateDTO;
import lk.ijse.dep13.backendexpensemanager.entity.IncomeExpense;
import lk.ijse.dep13.backendexpensemanager.repository.IncomeExpenseRepo;
import lk.ijse.dep13.backendexpensemanager.repository.UserRepo;
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
    private UserRepo userRepo;

    public ResponseEntity<ApiResponse> createIncomeExpense(String userName, IncomeExpenseDTO incomeExpenseDTO) {
        if (!userRepo.existsById(userName)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username");
        }

        IncomeExpense incomeExpense = new IncomeExpense();
        incomeExpense.setUserName(userName);
        incomeExpense.setType(incomeExpenseDTO.getType());
        incomeExpense.setDescription(incomeExpenseDTO.getDescription());
        incomeExpense.setAmount(incomeExpenseDTO.getAmount());
        incomeExpense.setTransactionDate(incomeExpenseDTO.getTransactionDate()!=null?incomeExpenseDTO.getTransactionDate():LocalDate.now());
        incomeExpenseRepo.save(incomeExpense);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse("Successfully created your %s record".formatted(incomeExpenseDTO.getType())));
    }

    public IncomeExpenseInfoDTO getIncomeExpense(Long id, String userName) {
        if (!userRepo.existsById(userName)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username");
        }
        IncomeExpense incomeExpense = incomeExpenseRepo.findByIdAndUserName(id, userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found for this user"));

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
        if (!userRepo.existsById(userName)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username");
        }
        List<IncomeExpense> incomeExpenses = incomeExpenseRepo.findIncomeExpenseByUserName(userName);
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
        if (!userRepo.existsById(updateDTO.getUserName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username");
        }

        IncomeExpense incomeExpense = incomeExpenseRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found"));

        if (!incomeExpense.getUserName().equals(updateDTO.getUserName())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not allowed to update this record");
        }

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
        return ResponseEntity.noContent().build();
    }

}
