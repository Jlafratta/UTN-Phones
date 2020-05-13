package edu.phones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync        //Esto
@EnableScheduling   //Y esto son para el scheduler del expiresSessions
public class SpringBootMain {

    public static void main(String[] args){
        SpringApplication.run(SpringBootMain.class, args);
    }
}
