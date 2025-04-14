package lk.ijse.dep13.backendexpensemanager;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "lk.ijse.dep13.backendexpensemanager.repository")
@EntityScan(basePackages = "lk.ijse.dep13.backendexpensemanager.model")
public class JpaConfig {

}