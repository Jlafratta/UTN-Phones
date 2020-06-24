package edu.phones.service;


import edu.phones.service.integration.IntegrationComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegrationService {

    @Autowired
    IntegrationComponent integrationComponent;

  //  public Pet getPet() {
   //     return integrationComponent.getPetsFromApi();
 //   }




}
