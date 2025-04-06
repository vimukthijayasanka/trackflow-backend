package lk.ijse.dep13.backendexpensemanager;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.sql.SQLException;

@Configuration
@EnableWebMvc
@ComponentScan
public class WebAppConfig {

    public WebAppConfig() throws SQLException {
    }
}
