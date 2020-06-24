package edu.phones.config;

import edu.phones.session.SessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@org.springframework.context.annotation.Configuration
@PropertySource("app.properties")
@EnableScheduling
public class Configuration {

    @Autowired
    SessionFilter sessionFilter;
    @Value("${db.driver}")
    String driver;
    @Value("${db.name}")
    String db;
    @Value("${db.host}")
    String host;
    @Value("${db.port}")
    int port;
    @Value("${db.user}")
    String username;
    @Value("${db.password}")
    String password;

    @Bean
    public Connection getConnection() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        Class.forName(driver).newInstance();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/" + db + "?user=" + username + "&password=" + password + "");
        return connection;
    }

    // Registro el filtro en spring
    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter);  //Genera un filtro con mi sessionFilter
        registration.addUrlPatterns("/apiBis/*");  //para todas las url que esten delante de /api/
        return registration;
    }

}
