package edu.phones.controller.web;

import edu.phones.controller.BillController;
import edu.phones.controller.CallController;
import edu.phones.controller.UserController;
import edu.phones.domain.Bill;
import edu.phones.domain.Call;
import edu.phones.domain.User;
import edu.phones.dto.AddCallDto;
import edu.phones.exceptions.alreadyExist.CallAlreadyExistsException;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.session.SessionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *      /api/* -> uso de clientes
 *      /backoffice/* -> uso de empleados
 */

@RestController
public class ClientWebController {

    CallController callController;
    BillController billController;
    UserController userController;
    SessionManager sessionManager;

    public ClientWebController(CallController callController, BillController billController, UserController userController, SessionManager sessionManager) {
        this.callController = callController;
        this.billController = billController;
        this.userController = userController;
        this.sessionManager = sessionManager;
    }

    /** Clients **/

    /* 2) Consulta de llamadas del usuario logueado por rango de fechas. */
    @GetMapping("/api/me/calls")
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

    /* 3) Consulta de facturas del usuario logueado por rango de fechas.*/
     @GetMapping("/api/me/bills")
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

    /* 4) Consulta de TOP 10 destinos más llamados por el usuario. */
    //TODO Consulta top 10 destinos

    /** Employees **/

    /* 2) Manejo de clientes. */
    // realizado en UserWebController

    /* 3) Alta , baja y suspensión de líneas. */
    // realizado en PhoneLineWebController

    /* 4) Consulta de tarifas. */
    //TODO Organizar que tipos de consultas y donde realizarlas

    /* 5) Consulta de llamadas por usuario. */
    @GetMapping("/backoffice/calls")
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

    /* 6) Consulta de facturación .
     *  La facturación se hará
     *  directamente por un
     *  proceso interno en la
     *  base datos. */
    //TODO Hacer consulta de facturas para backoffice

    /** Infrastructure **/

    /* Agregado de llamadas */
    @PostMapping("/inf")
    public ResponseEntity<Call> addCall(@RequestBody AddCallDto dto, @RequestHeader("Authorization") String sessionToken) throws CallAlreadyExistsException {
        Call call = callController.createCall(dto);
        return ResponseEntity.created(getLocation(call)).build();
    }

    /** Extras **/

    private User getCurrentUser(String sessionToken) throws UserNotExistException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(UserNotExistException::new);
    }

    Date dateConverter(String toConvert) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(toConvert);
    }

    private URI getLocation(Call call) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(call.getCallId())
                .toUri();
    }
}
