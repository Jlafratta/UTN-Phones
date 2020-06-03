package edu.phones.controller.web;

import edu.phones.controller.CallController;
import edu.phones.dto.CallQuantityDto;
import edu.phones.session.SessionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/call")
public class CallWebController {

    CallController callController;
    SessionManager sessionManager;

    public CallWebController(CallController callController, SessionManager sessionManager) {
        this.callController = callController;
        this.sessionManager = sessionManager;
    }

    @GetMapping("/xmas")
    public ResponseEntity<CallQuantityDto> getCantCallsFromChristmas(@RequestHeader("Authorization") String sessionToken){

        CallQuantityDto dto = callController.getCallsFromChristmas();

        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
