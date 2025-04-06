package lk.ijse.dep13.backendexpensemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "lk.ijse.dep13.backendexpensemanager")
public class BackendExpenseManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendExpenseManagerApplication.class, args);
    }
}