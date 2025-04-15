package lk.ijse.dep13.backendexpensemanager.dto;

import lk.ijse.dep13.backendexpensemanager.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeExpenseAllInfoDTO {
    private String userName;
    private TransactionType type;
    private String description;
    private BigDecimal amount;
    private LocalDate transactionDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
