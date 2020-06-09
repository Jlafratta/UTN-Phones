package edu.phones.controller.web;

import edu.phones.controller.CallController;
import edu.phones.domain.Call;
import edu.phones.dto.AddCallDto;
import edu.phones.session.SessionManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/inf")
public class InfWebController {

    CallController callController;
    SessionManager sessionManager;


    public InfWebController(CallController callController, SessionManager sessionManager) {
        this.callController = callController;
        this.sessionManager = sessionManager;
    }
/**
    @PostMapping
    public ResponseEntity<Call> addCall(@RequestBody AddCallDto dto, @RequestHeader("Authorization") String sessionToken){

        
    }**/
}
