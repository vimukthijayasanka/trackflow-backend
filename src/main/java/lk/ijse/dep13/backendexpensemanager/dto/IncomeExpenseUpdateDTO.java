package lk.ijse.dep13.backendexpensemanager.dto;

import jakarta.validation.constraints.NotBlank;
import lk.ijse.dep13.backendexpensemanager.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeExpenseUpdateDTO {
    private Long id;
    private TransactionType type;
    private String description;
    private BigDecimal amount;
    private LocalDate transactionDate;
}
