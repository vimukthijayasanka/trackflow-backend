package lk.ijse.dep13.backendexpensemanager.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class IncomeExpenseHttpController {
       @PostMapping(consumes = "application/json")
        public String createIncomeExpense() {
            return "save a transaction";
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
