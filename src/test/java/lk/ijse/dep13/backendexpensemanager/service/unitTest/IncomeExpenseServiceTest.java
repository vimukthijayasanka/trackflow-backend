package lk.ijse.dep13.backendexpensemanager.service.unitTest;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseDTO;
import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseInfoDTO;
import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseUpdateDTO;
import lk.ijse.dep13.backendexpensemanager.entity.IncomeExpense;
import lk.ijse.dep13.backendexpensemanager.enums.AuditAction;
import lk.ijse.dep13.backendexpensemanager.enums.AuditLogCategory;
import lk.ijse.dep13.backendexpensemanager.enums.TransactionType;
import lk.ijse.dep13.backendexpensemanager.repository.IncomeExpenseRepo;
import lk.ijse.dep13.backendexpensemanager.service.AuditLogService;
import lk.ijse.dep13.backendexpensemanager.service.IncomeExpenseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IncomeExpenseServiceTest {

    @InjectMocks
    private IncomeExpenseService incomeExpenseService;

    @Mock
    AuditLogService auditLogService;

    @Mock
    private IncomeExpenseRepo incomeExpenseRepo;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCreateIncomeExpense_Success () {
        String username = "Joel_Miller";
        IncomeExpenseDTO incomeExpenseDTO = new IncomeExpenseDTO(username,TransactionType.INCOME,"Salary",new BigDecimal("100000.00"), LocalDate.now());
        ResponseEntity<?> response = incomeExpenseService.createIncomeExpense(username, incomeExpenseDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(incomeExpenseRepo).save(any(IncomeExpense.class));
        verify(auditLogService).log(eq(username), any(), any(), any(), contains("Created"));
    }

    @Test
    void testCreateIncomeExpense_NegativeAmount_ShouldFailValidation() {
        String username = "Joel_Miller";
        IncomeExpenseDTO dto = new IncomeExpenseDTO(
                username,
                TransactionType.INCOME,
                "Salary",
                new BigDecimal("-1000"),
                LocalDate.now()
        );

        assertThrows(ConstraintViolationException.class, () -> {
            incomeExpenseService.createIncomeExpense(username, dto);
        });
    }
    
    @Test
    void testGetIncomeExpense_Success() {
        String username = "Joel_Miller";
        int id = 1;
        IncomeExpense incomeExpense = new IncomeExpense();
        incomeExpense.setId(id);
        incomeExpense.setUserName(username);
        incomeExpense.setType(TransactionType.INCOME);
        incomeExpense.setDescription("Salary");
        incomeExpense.setAmount(new BigDecimal("100000.00"));
        incomeExpense.setTransactionDate(LocalDate.now());

        when(incomeExpenseRepo.findByIdAndUserName((long) id, username)).thenReturn(Optional.of(incomeExpense));

        IncomeExpenseInfoDTO expectedDTO = new IncomeExpenseInfoDTO(
                (long) id,
                TransactionType.INCOME,
                "Salary",
                new BigDecimal("100000.00"),
                LocalDate.now()
        );

        //Act
        IncomeExpenseInfoDTO result = incomeExpenseService.getIncomeExpense((long) id, username);

        // Assert
        assertNotNull(result);
        assertEquals(expectedDTO.getId(), result.getId());
        assertEquals(expectedDTO.getType(), result.getType());
        assertEquals(expectedDTO.getDescription(), result.getDescription());
        assertEquals(expectedDTO.getAmount(), result.getAmount());
        assertEquals(expectedDTO.getTransactionDate(), result.getTransactionDate());

        // Verify that the log was created for the 'READ' action
        verify(auditLogService).log(eq(username), eq(AuditAction.READ), eq(AuditLogCategory.TRANSACTION), eq(String.valueOf(id)), contains("Read"));
    }

    @Test
    void testGetAllIncomeExpense_Success() {
        // Arrange
        String username = "Joel_Miller";

        IncomeExpense tx1 = new IncomeExpense();
        tx1.setUserName(username);
        tx1.setType(TransactionType.INCOME);
        tx1.setDescription("Salary");
        tx1.setAmount(new BigDecimal("100000.00"));
        tx1.setTransactionDate(LocalDate.of(2024, 1, 1));
        tx1.setCreatedAt(LocalDateTime.of(2024, 1, 1, 12, 0));
        tx1.setUpdatedAt(LocalDateTime.of(2024, 1, 2, 12, 0));

        IncomeExpense tx2 = new IncomeExpense();
        tx2.setUserName(username);
        tx2.setType(TransactionType.EXPENSE);
        tx2.setDescription("Rent");
        tx2.setAmount(new BigDecimal("40000.00"));
        tx2.setTransactionDate(LocalDate.of(2024, 1, 3));
        tx2.setCreatedAt(LocalDateTime.of(2024, 1, 3, 10, 0));
        tx2.setUpdatedAt(LocalDateTime.of(2024, 1, 4, 10, 0));

        List<IncomeExpense> mockTransactions = List.of(tx1, tx2);
        when(incomeExpenseRepo.findIncomeExpenseByUserName(username)).thenReturn(mockTransactions);

        // Act
        List<IncomeExpenseInfoDTO> result = incomeExpenseService.getAllIncomeExpense(username);

        // Assert
        assertEquals(2, result.size());
        assertEquals("Salary", result.get(0).getDescription());
        assertEquals("Rent", result.get(1).getDescription());
        assertEquals(new BigDecimal("100000.00"), result.get(0).getAmount());
        assertEquals(TransactionType.EXPENSE, result.get(1).getType());

        // Verify logging
        verify(auditLogService).log(
                eq(username),
                eq(AuditAction.READ),
                eq(AuditLogCategory.TRANSACTION),
                eq("NULL"),
                contains("Read 2 records for " + username)
        );

        // Verify repo call
        verify(incomeExpenseRepo).findIncomeExpenseByUserName(username);
    }

    @Test
    void testUpdateIncomeExpense_Success() {
        // Arrange
        int id = 1;
        String username = "Joel_Miller";

        IncomeExpense existingIncomeExpense = new IncomeExpense();
        existingIncomeExpense.setId(id);
        existingIncomeExpense.setUserName(username);
        existingIncomeExpense.setType(TransactionType.INCOME);
        existingIncomeExpense.setDescription("Old Description");
        existingIncomeExpense.setAmount(new BigDecimal("1000.00"));
        existingIncomeExpense.setTransactionDate(LocalDate.now().minusDays(5));
        existingIncomeExpense.setCreatedAt(LocalDateTime.now().minusDays(5));
        existingIncomeExpense.setUpdatedAt(LocalDateTime.now().minusDays(5));

        IncomeExpenseUpdateDTO updateDTO = new IncomeExpenseUpdateDTO(
                TransactionType.EXPENSE,
                "New Laptop",
                new BigDecimal("500000.00"),
                LocalDate.now()
        );

        when(incomeExpenseRepo.findByIdAndUserName((long)id, username)).thenReturn(Optional.of(existingIncomeExpense));
        when(incomeExpenseRepo.save(any(IncomeExpense.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        IncomeExpenseInfoDTO result = incomeExpenseService.updateIncomeExpense((long)id,username, updateDTO);

        // Assert
        assertNotNull(result);
        assertEquals(TransactionType.EXPENSE, result.getType());
        assertEquals("New Laptop", result.getDescription());
        assertEquals(new BigDecimal("500000.00"), result.getAmount());

        verify(incomeExpenseRepo).findByIdAndUserName((long)id, username);
        verify(incomeExpenseRepo).save(existingIncomeExpense);
        verify(auditLogService).log(eq(username), eq(AuditAction.UPDATE), eq(AuditLogCategory.TRANSACTION), eq(String.valueOf(id)), contains("Update"));
    }

    @Test
    void testDeleteIncomeExpense_Success() {
        // Arrange
        int id = 1;
        String username = "Joel_Miller";

        // Set up a mock transaction to be deleted
        IncomeExpense existingIncomeExpense = new IncomeExpense();
        existingIncomeExpense.setId(id);
        existingIncomeExpense.setUserName(username);
        existingIncomeExpense.setType(TransactionType.INCOME);
        existingIncomeExpense.setDescription("Old Salary");
        existingIncomeExpense.setAmount(new BigDecimal("100000.00"));
        existingIncomeExpense.setTransactionDate(LocalDate.now().minusDays(10));
        existingIncomeExpense.setCreatedAt(LocalDateTime.now().minusDays(10));
        existingIncomeExpense.setUpdatedAt(LocalDateTime.now().minusDays(5));

        // Mock the behavior of the repository
        when(incomeExpenseRepo.findByIdAndUserName((long)id, username)).thenReturn(Optional.of(existingIncomeExpense));
        doNothing().when(incomeExpenseRepo).delete(existingIncomeExpense);

        // Act
        ResponseEntity<?> response = incomeExpenseService.deleteIncomeExpense((long)id, username);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verify that the repository's methods were called
        verify(incomeExpenseRepo).findByIdAndUserName((long)id, username);
        verify(incomeExpenseRepo).delete(existingIncomeExpense);

        // Verify that the audit log service was called
        verify(auditLogService).log(
                eq(username),
                eq(AuditAction.DELETE),
                eq(AuditLogCategory.TRANSACTION),
                eq(String.valueOf(id)),
                contains("Delete the transaction from user - " + username)
        );
    }

}
