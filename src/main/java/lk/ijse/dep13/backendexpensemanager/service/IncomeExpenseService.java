package lk.ijse.dep13.backendexpensemanager.service;

import lk.ijse.dep13.backendexpensemanager.dto.ApiResponse;
import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseDTO;
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

    public void getIncomeExpense(IncomeExpense incomeExpense) {

    }

    public void getAllIncomeExpense() {

    }

    public void updateIncomeExpense(IncomeExpense incomeExpense) {

    }

    public void deleteIncomeExpense(IncomeExpense incomeExpense) {

    }

}
