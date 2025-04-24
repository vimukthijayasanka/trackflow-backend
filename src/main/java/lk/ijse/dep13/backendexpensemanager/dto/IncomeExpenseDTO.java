package lk.ijse.dep13.backendexpensemanager.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lk.ijse.dep13.backendexpensemanager.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class IncomeExpenseDTO {
    private String userName;
    @NotNull(message = "Transaction type is required")
    private TransactionType type;
    @NotBlank(message = "Description is required")
    private String description;
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount should be positive")
    private BigDecimal amount;
    private LocalDate transactionDate;
}
