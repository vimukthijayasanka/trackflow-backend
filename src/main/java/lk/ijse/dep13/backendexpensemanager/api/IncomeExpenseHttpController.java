package lk.ijse.dep13.backendexpensemanager.api;

import lk.ijse.dep13.backendexpensemanager.dto.ApiResponse;
import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseDTO;
import lk.ijse.dep13.backendexpensemanager.service.IncomeExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class IncomeExpenseHttpController {

    @Autowired
    IncomeExpenseService incomeExpenseService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createIncomeExpense(@SessionAttribute(value="user")String user, @RequestBody IncomeExpenseDTO incomeExpenseDTO) {
        return incomeExpenseService.createIncomeExpense(user, incomeExpenseDTO);
    }

    @GetMapping("/{id}")
    public String getIncomeExpense(@PathVariable("id") Long id) {
        return "get transaction with ID: " + id;
    }

    @GetMapping
    public String getAllIncomeExpense() {
        return "get all transactions";
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    public String updateIncomeExpense(@PathVariable("id") Long id) {
        return "update transaction with ID: " + id;
    }

    @DeleteMapping("/{id}")
    public String deleteIncomeExpense(@PathVariable("id") Long id) {
        return "delete transaction with ID: " + id;
    }
}
