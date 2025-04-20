package lk.ijse.dep13.backendexpensemanager.service.integrationTest;

import lk.ijse.dep13.backendexpensemanager.dto.IncomeExpenseDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
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
        user.setUserName("Joel Miller");
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
    }

    @Test
    void testCreateIncomeExpense_Success() {
      IncomeExpenseDTO incomeExpenseDTO = new IncomeExpenseDTO();
      incomeExpenseDTO.setType(TransactionType.INCOME);
      incomeExpenseDTO.setDescription("Salary");
      incomeExpenseDTO.setAmount(new BigDecimal("10000.00"));
      incomeExpenseDTO.setTransactionDate(LocalDate.now());

      ResponseEntity<?> response = incomeExpenseService.createIncomeExpense("Joel Miller", incomeExpenseDTO);
      assertEquals(HttpStatus.CREATED, response.getStatusCode());

      List<IncomeExpense> all = incomeExpenseRepo.findAll();
      assertEquals(1, all.size());
      assertEquals("Salary", all.get(0).getDescription());
    }

    @Test
    void getIncomeExpense() {
    }

    @Test
    void getAllIncomeExpense() {
    }

    @Test
    void updateIncomeExpense() {
    }

    @Test
    void deleteIncomeExpense() {
    }
}