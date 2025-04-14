package lk.ijse.dep13.backendexpensemanager;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "lk.ijse.dep13.backendexpensemanager.repository")
@EntityScan(basePackages = "lk.ijse.dep13.backendexpensemanager.entity")
public class JpaConfig {

}