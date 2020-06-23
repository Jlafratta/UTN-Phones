package edu.phones.controller.web;

import edu.phones.controller.CallController;
import edu.phones.controller.UserController;
import edu.phones.domain.Call;
import edu.phones.domain.User;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.session.SessionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/calls")
public class CallWebController {

    CallController callController;
    UserController userController;
    SessionManager sessionManager;

    public CallWebController(CallController callController, UserController userController, SessionManager sessionManager) {
        this.callController = callController;
        this.userController = userController;
        this.sessionManager = sessionManager;
    }
/**
    @GetMapping()
    public ResponseEntity<List<Call>> getCalls(@RequestParam(value = "username", required = false) String username, @RequestHeader("Authorization") String sessionToken){

        List<Call> calls;
        if (username != null){
            User user = userController.getByUsername(username);
            calls = user != null ? callController.getByOriginUser(user) : new ArrayList<>();
        }else {
            calls = callController.getAll();
        }

        return (calls.size() > 0) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
**/
}
