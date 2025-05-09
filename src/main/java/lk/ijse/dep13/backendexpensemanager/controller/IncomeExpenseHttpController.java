package lk.ijse.dep13.backendexpensemanager.controller;

import jakarta.validation.Valid;
import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseInfoDTO;
import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseDTO;
import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseUpdateDTO;
import lk.ijse.dep13.backendexpensemanager.service.IncomeExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@Validated
@CrossOrigin
public class IncomeExpenseHttpController {

    @Autowired
    IncomeExpenseService incomeExpenseService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> createIncomeExpense(@SessionAttribute(value="user")String user,@Valid @RequestBody IncomeExpenseDTO incomeExpenseDTO) {
        return incomeExpenseService.createIncomeExpense(user, incomeExpenseDTO);
    }

    @GetMapping("/{id}")
    public IncomeExpenseInfoDTO getIncomeExpense(@PathVariable("id") Long id, @SessionAttribute(value="user")String userName) {
        return incomeExpenseService.getIncomeExpense(id, userName);
    }

    @GetMapping
    public List<IncomeExpenseInfoDTO> getAllIncomeExpense(@SessionAttribute(value="user")String userName) {
        return incomeExpenseService.getAllIncomeExpense(userName);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json")
    public IncomeExpenseInfoDTO updateIncomeExpense(@PathVariable("id") Long id, @SessionAttribute(value="user")String userName, @Valid @RequestBody IncomeExpenseUpdateDTO updateDTO) {
        return incomeExpenseService.updateIncomeExpense(id,userName, updateDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIncomeExpense(@SessionAttribute(value="user")String userName, @PathVariable("id") Long id) {
        return incomeExpenseService.deleteIncomeExpense(id, userName);
    }
}
