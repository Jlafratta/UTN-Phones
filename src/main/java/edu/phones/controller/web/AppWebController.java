package edu.phones.controller.web;

import edu.phones.controller.BillController;
import edu.phones.controller.CallController;
import edu.phones.domain.Bill;
import edu.phones.domain.Call;
import edu.phones.domain.User;
import edu.phones.dto.CallRequestDto;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.session.SessionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    /********************************************************/

    @GetMapping("/call/duration")
    public ResponseEntity<CallRequestDto> getCallsDuration(@RequestParam(value = "month") String month,
                                                           @RequestHeader("Authorization") String sessionToken) throws UserNotExistException, ParseException {
        User currentUser = getCurrentUser(sessionToken);
        if (month == null || Integer.parseInt(month) < 1 || Integer.parseInt(month) > 12 ){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {

            String date = "01-" + month + "-" + Calendar.getInstance().get(Calendar.YEAR);
            CallRequestDto dto = callController.getDurationByMonth(currentUser, date);
            return ResponseEntity.ok(dto);
        }
    }

    /********************************************************/


    @GetMapping("/call")
    public ResponseEntity<List<Call>> getCalls(@RequestParam(value = "from", required = false) String from,
                                               @RequestParam(value = "to", required = false) String to,
                                               @RequestHeader("Authorization") String sessionToken) throws UserNotExistException, ParseException {
        User currentUser = getCurrentUser(sessionToken);
        List<Call> calls;
        if (from != null && to != null){
            Date dFrom = dateConverter(from);
            Date dTo = dateConverter(to);
            calls = callController.getByOriginUserFilterByDate(currentUser, dFrom, dTo);
        } else {
            calls = callController.getByOriginUser(currentUser);
        }
        return (calls.size() > 0) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/bill")
    public ResponseEntity<List<Bill>> getBills(@RequestParam(value = "from", required = false) String from,
                                               @RequestParam(value = "to", required = false) String to,
                                               @RequestHeader("Authorization") String sessionToken) throws UserNotExistException, ParseException {
        User currentUser = getCurrentUser(sessionToken);
        List<Bill> bills;
        if(from != null && to != null){
            Date dFrom = dateConverter(from);
            Date dTo = dateConverter(to);
            bills = billController.getByUserFilterByDate(currentUser, dFrom, dTo);
        }else {
            bills = billController.getByUser(currentUser);
        }
        return (bills.size() > 0) ? ResponseEntity.ok(bills) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    private User getCurrentUser(String sessionToken) throws UserNotExistException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(UserNotExistException::new);
    }

    Date dateConverter(String toConvert) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(toConvert);
    }
}
