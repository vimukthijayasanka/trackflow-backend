package lk.ijse.dep13.backendexpensemanager.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class IncomeExpenseHttpController {
    @PostMapping(consumes = "application/json")
    public String createIncomeExpense(){
        return "save a transaction";
    }

    @GetMapping("/{id:^\\d+$}")
    public String getIncomeExpense(){
        return "get a transaction";
    }

    @GetMapping
    public String getAllIncomeExpense(){
        return "get all transactions";
    }

    @PatchMapping(value = "/{id:^\\d+$}", consumes = "application/json")
    public String updateIncomeExpense(){
        return "update transaction";
    }

    @DeleteMapping("/{id:^\\d+$}")
    public String deleteIncomeExpense(){
        return "delete transaction";
    }
}
