package lk.ijse.dep13.backendexpensemanager.service.integrationTest;

import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseDTO;
import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseInfoDTO;
import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseUpdateDTO;
import lk.ijse.dep13.backendexpensemanager.entity.IncomeExpense;
import lk.ijse.dep13.backendexpensemanager.entity.User;
import lk.ijse.dep13.backendexpensemanager.enums.TransactionType;
import lk.ijse.dep13.backendexpensemanager.repository.IncomeExpenseRepo;
import lk.ijse.dep13.backendexpensemanager.repository.UserRepo;
import lk.ijse.dep13.backendexpensemanager.service.AuditLogService;
import lk.ijse.dep13.backendexpensemanager.service.IncomeExpenseService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // uses application-test.properties instead of application.properties
@Transactional
class IncomeExpenseIntegrationTest {

    @Autowired
    private IncomeExpenseService incomeExpenseService;

    @Autowired
    private IncomeExpenseRepo incomeExpenseRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private AuditLogService auditLogService;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUserName("Joel_Miller");
        user.setPassword("hashed_password");
        user.setFirstName("Joel");
        user.setLastName("Miller");
        user.setEmail("joelmiller@gmail.com");
        user.setDob(LocalDate.of(1990, 1, 1));
        user.setProfilePictureUrl("https://i.ibb.co/9292p2C/joel-miller.jpg");
        userRepo.save(user);
    }

    @AfterEach
    void tearDown() {
        incomeExpenseRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    void testCreateIncomeExpense_Success() {
      LocalDate fixedDate = LocalDate.of(2024, 4, 20);

      IncomeExpenseDTO incomeExpenseDTO = new IncomeExpenseDTO();
      incomeExpenseDTO.setType(TransactionType.INCOME);
      incomeExpenseDTO.setDescription("Salary");
      incomeExpenseDTO.setAmount(new BigDecimal("10000.00"));
      incomeExpenseDTO.setTransactionDate(fixedDate);

      ResponseEntity<?> response = incomeExpenseService.createIncomeExpense("Joel_Miller", incomeExpenseDTO);
      assertEquals(HttpStatus.CREATED, response.getStatusCode());

      List<IncomeExpense> all = incomeExpenseRepo.findAll();
      assertEquals(1, all.size());

      IncomeExpense saved = all.get(0);
      assertEquals("Salary", saved.getDescription());
      assertEquals(fixedDate, saved.getTransactionDate());
      assertEquals(new BigDecimal("10000.00"), saved.getAmount());
      assertEquals(TransactionType.INCOME, saved.getType());
      assertEquals("Joel_Miller", saved.getUserName());

//      Mockito.verify(auditLogService).log(Mockito.eq("Joel_Miller"), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.contains("Created"));
    }

    @Test
    void getIncomeExpense() {
        LocalDate fixedDate = LocalDate.of(2024, 4, 20);

        IncomeExpense incomeExpense = new IncomeExpense();
        incomeExpense.setUserName("Joel_Miller");
        incomeExpense.setType(TransactionType.INCOME);
        incomeExpense.setDescription("Salary");
        incomeExpense.setAmount(new BigDecimal("10000.00"));
        incomeExpense.setTransactionDate(fixedDate);
        IncomeExpense saved = incomeExpenseRepo.save(incomeExpense);

        IncomeExpenseInfoDTO infoDTO = incomeExpenseService.getIncomeExpense((long) saved.getId(), "Joel_Miller");
        assertNotNull(infoDTO);
        assertEquals("Joel_Miller", infoDTO.getUserName());
        assertEquals(TransactionType.INCOME, infoDTO.getType());
        assertEquals("Salary", infoDTO.getDescription());
        assertEquals(new BigDecimal("10000.00"), infoDTO.getAmount());
        assertEquals(fixedDate, infoDTO.getTransactionDate());
    }

    @Test
    void getAllIncomeExpense() {
        LocalDate fixedDate = LocalDate.of(2024, 4, 20);

        IncomeExpense incomeExpense1 = new IncomeExpense();
        incomeExpense1.setUserName("Joel_Miller");
        incomeExpense1.setType(TransactionType.INCOME);
        incomeExpense1.setDescription("Salary");
        incomeExpense1.setAmount(new BigDecimal("10000.00"));
        incomeExpense1.setTransactionDate(fixedDate);
        incomeExpenseRepo.save(incomeExpense1);

        IncomeExpense incomeExpense2 = new IncomeExpense();
        incomeExpense2.setUserName("Joel_Miller");
        incomeExpense2.setType(TransactionType.EXPENSE);
        incomeExpense2.setDescription("Rent");
        incomeExpense2.setAmount(new BigDecimal("40000.00"));
        incomeExpense2.setTransactionDate(fixedDate);
        incomeExpenseRepo.save(incomeExpense2);

        List<IncomeExpenseInfoDTO> listOfDTOs = incomeExpenseService.getAllIncomeExpense("Joel_Miller");
        assertEquals(2, listOfDTOs.size());
        assertEquals("Salary", listOfDTOs.get(0).getDescription());
        assertEquals("Rent", listOfDTOs.get(1).getDescription());
    }

    @Test
    void updateIncomeExpense() {

        LocalDate fixedDate = LocalDate.of(2024, 4, 20);
        LocalDate updatedDate = LocalDate.of(2025, 10, 12);

        IncomeExpense incomeExpense = new IncomeExpense();
        incomeExpense.setUserName("Joel_Miller");
        incomeExpense.setType(TransactionType.INCOME);
        incomeExpense.setDescription("Old Description");
        incomeExpense.setAmount(new BigDecimal("1000.00"));
        incomeExpense.setTransactionDate(fixedDate);
        IncomeExpense saved = incomeExpenseRepo.save(incomeExpense);

        IncomeExpenseUpdateDTO updateDTO = new IncomeExpenseUpdateDTO();
        updateDTO.setUserName("Joel_Miller");
        updateDTO.setType(TransactionType.EXPENSE);
        updateDTO.setDescription("New Laptop");
        updateDTO.setAmount(new BigDecimal("500000.00"));
        updateDTO.setTransactionDate(updatedDate);

        incomeExpenseService.updateIncomeExpense((long) saved.getId(), updateDTO);

        IncomeExpense updated = incomeExpenseRepo.findById((long) saved.getId()).orElseThrow();
        assertEquals(TransactionType.EXPENSE, updated.getType());
        assertEquals("New Laptop", updated.getDescription());
        assertEquals(new BigDecimal("500000.00"), updated.getAmount());
        assertEquals(updatedDate, updated.getTransactionDate());
    }

    @Test
    void deleteIncomeExpense() {
        LocalDate fixedDate = LocalDate.of(2024, 4, 20);

        IncomeExpense incomeExpense = new IncomeExpense();
        incomeExpense.setUserName("Joel_Miller");
        incomeExpense.setType(TransactionType.INCOME);
        incomeExpense.setDescription("Old Description");
        incomeExpense.setAmount(new BigDecimal("1000.00"));
        incomeExpense.setTransactionDate(fixedDate);
        IncomeExpense saved = incomeExpenseRepo.save(incomeExpense);

        ResponseEntity<?> response = incomeExpenseService.deleteIncomeExpense((long) saved.getId(), "Joel_Miller");
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(incomeExpenseRepo.findById((long) saved.getId()).isPresent());
    }

    @Test
    void testCreateIncomeExpense_NegativeAmount_ShouldFail() {
        IncomeExpenseDTO dto = new IncomeExpenseDTO();
        dto.setType(TransactionType.EXPENSE);
        dto.setDescription("Negative Expense");
        dto.setAmount(new BigDecimal("-1000.00"));
        dto.setTransactionDate(LocalDate.now());

        assertThrows(RuntimeException.class, () -> incomeExpenseService.createIncomeExpense("Joel_Miller", dto));
    }

    @Test
    void testUpdateIncomeExpense_InvalidUser_ShouldFail() {
        IncomeExpense entity = new IncomeExpense(1, "Joel_Miller", TransactionType.INCOME, "Desc", new BigDecimal("1000.00"), LocalDate.now(), LocalDateTime.now(),LocalDateTime.now());
        IncomeExpense saved = incomeExpenseRepo.save(entity);

        IncomeExpenseUpdateDTO updateDTO = new IncomeExpenseUpdateDTO();
        updateDTO.setUserName("Wrong_User");
        updateDTO.setType(TransactionType.EXPENSE);
        updateDTO.setDescription("Hacked!");
        updateDTO.setAmount(new BigDecimal("9999.99"));
        updateDTO.setTransactionDate(LocalDate.now());

        assertThrows(RuntimeException.class, () -> incomeExpenseService.updateIncomeExpense((long) saved.getId(), updateDTO));
    }

    @ParameterizedTest
    @CsvSource({
            "Lunch, 1500.00, EXPENSE",
            "Stock Profit, 20000.00, INCOME",
            "Books, 3500.00, EXPENSE"
    })
    void testCreateIncomeExpenseParameterized(String description, BigDecimal amount, TransactionType type) {
        IncomeExpenseDTO dto = new IncomeExpenseDTO();
        dto.setDescription(description);
        dto.setAmount(amount);
        dto.setTransactionDate(LocalDate.of(2024, 4, 20));
        dto.setType(type);

        ResponseEntity<?> response = incomeExpenseService.createIncomeExpense("Joel_Miller", dto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        IncomeExpense created = incomeExpenseRepo.findAll().stream()
                .filter(e -> e.getDescription().equals(description)).findFirst().orElseThrow();
        assertEquals(description, created.getDescription());
        assertEquals(amount, created.getAmount());
        assertEquals(type, created.getType());
    }
}