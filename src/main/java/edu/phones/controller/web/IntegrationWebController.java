package edu.phones.controller.web;


        import edu.phones.service.IntegrationService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class IntegrationWebController {

    @Autowired
    IntegrationService integrationService;

    //@GetMapping("/pet")


  //  public Pet getPet() {
    //    return integrationService.getPet();
    //}

}
