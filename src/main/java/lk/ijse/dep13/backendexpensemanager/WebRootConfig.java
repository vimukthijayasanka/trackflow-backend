package lk.ijse.dep13.backendexpensemanager;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.context.annotation.RequestScope;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@PropertySource("classpath:/application.properties")
public class WebRootConfig {

    public WebRootConfig() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
    }

    @Bean
    @RequestScope /// need to clarify
    public Connection getConnection(@Value("${app.datasource.url}")
                                    String url, @Value("${app.datasource.username}") String username,
                                    @Value("${app.database.password}") String password ) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
