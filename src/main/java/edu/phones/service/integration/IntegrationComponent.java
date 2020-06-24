package edu.phones.service.integration;



import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;


@Component
public class IntegrationComponent {

    private RestTemplate rest;
    private static String url = "http://localhost:8081/";

    @PostConstruct
    private void init() {
        rest = new RestTemplateBuilder()
                .build();
    }

   // public Pet getPetsFromApi() {
  //      return rest.getForObject(url, Pet.class);
    //}

}
