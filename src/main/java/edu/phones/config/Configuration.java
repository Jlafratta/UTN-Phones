package edu.phones.config;

import edu.phones.session.BackofficeSessionFilter;
import edu.phones.session.InfrastructureSessionFilter;
import edu.phones.session.SessionFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@org.springframework.context.annotation.Configuration
@EnableSwagger2
@PropertySource("app.properties")
@EnableScheduling
public class Configuration {

    @Autowired
    SessionFilter sessionFilter;

    @Autowired
    BackofficeSessionFilter backofficeSessionFilter;

    @Autowired
    InfrastructureSessionFilter infrastructureSessionFilter;

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

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("edu.phones"))
                .paths(PathSelectors.any())
                .build();
    }

    // Registro el filtro de clientes en spring
    @Bean
    public FilterRegistrationBean clientFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sessionFilter);  //Genera un filtro con mi sessionFilter
        registration.addUrlPatterns("/api/*");  //para todas las url que esten delante de /api/
        return registration;
    }

    @Bean
    public FilterRegistrationBean backofficeFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(backofficeSessionFilter);  //Genera un filtro con mi sessionFilter
        registration.addUrlPatterns("/backoffice/*");  //para todas las url que esten delante de /api/
        return registration;
    }

    @Bean
    public FilterRegistrationBean infrastructureFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(infrastructureSessionFilter);  //Genera un filtro con mi sessionFilter
        registration.addUrlPatterns("/inf/*");  //para todas las url que esten delante de /api/
        return registration;
    }

}
