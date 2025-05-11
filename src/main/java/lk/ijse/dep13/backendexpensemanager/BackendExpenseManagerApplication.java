package lk.ijse.dep13.backendexpensemanager;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "lk.ijse.dep13.backendexpensemanager")
public class BackendExpenseManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendExpenseManagerApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}