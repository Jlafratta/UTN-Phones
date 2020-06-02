package edu.phones.controller.web;

import edu.phones.controller.BillController;
import edu.phones.controller.CallController;
import edu.phones.domain.Call;
import edu.phones.domain.User;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.session.SessionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/me")
public class AppWebController {

    CallController callController;
    BillController billController;
    SessionManager sessionManager;

    public AppWebController(CallController callController, BillController billController, SessionManager sessionManager) {
        this.callController = callController;
        this.billController = billController;
        this.sessionManager = sessionManager;
    }

    @GetMapping
    public ResponseEntity<List<Call>> getCalls(@RequestParam(value = "from", required = false) String from,
                                               @RequestParam(value = "to", required = false) String to,
                                               @RequestHeader("Authorization") String sessionToken) throws UserNotExistException, ParseException {
        User currentUser = getCurrentUser(sessionToken);
        List<Call> calls;
        if (from != null && to != null){
            Date dFrom = new java.sql.Date(dateConverter(from).getTime());
            Date dTo = new java.sql.Date(dateConverter(to).getTime());
            calls = callController.getByOriginUserFilterByDate(currentUser, dFrom, dTo);
        } else {
            calls = callController.getByOriginUser(currentUser);
        }
        return (calls.size() > 0) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private User getCurrentUser(String sessionToken) throws UserNotExistException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(UserNotExistException::new);
    }

    Date dateConverter(String toConvert) throws ParseException {
        return new SimpleDateFormat("yyyy/MM/dd").parse(toConvert);
    }
}
