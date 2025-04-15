package lk.ijse.dep13.backendexpensemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(scanBasePackages = "lk.ijse.dep13.backendexpensemanager")
public class BackendExpenseManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendExpenseManagerApplication.class, args);
    }
}