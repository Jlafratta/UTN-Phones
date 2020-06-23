package edu.phones.controller.web;

import edu.phones.controller.*;
import edu.phones.domain.*;
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
    PhoneLineController lineController;
    TariffController tariffController;
    SessionManager sessionManager;


    public ClientWebController(CallController callController, BillController billController, UserController userController, PhoneLineController lineController, TariffController tariffController, SessionManager sessionManager) {
        this.callController = callController;
        this.billController = billController;
        this.userController = userController;
        this.lineController = lineController;
        this.tariffController = tariffController;
        this.sessionManager = sessionManager;
    }

    /** Clients **/

    /* 2) Consulta de llamadas del usuario logueado por rango de fechas. */
    @GetMapping("/api/calls")
    public ResponseEntity<List<Call>> getCalls(@RequestParam(value = "from", required = false) String from,
                                               @RequestParam(value = "to", required = false) String to,
                                               @RequestHeader("Authorization") String sessionToken) throws UserNotExistException, ParseException {
        User currentUser = getCurrentUser(sessionToken);
        List<Call> calls;
        calls = (from != null && to != null)
                ? callController.getByOriginUserFilterByDate(currentUser, dateConverter(from), dateConverter(to))
                : callController.getByOriginUser(currentUser);
        return (calls.size() > 0) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /* 3) Consulta de facturas del usuario logueado por rango de fechas.*/
    @GetMapping("/api/bills")
    public ResponseEntity<List<Bill>> getBills(@RequestParam(value = "from", required = false) String from,
                                               @RequestParam(value = "to", required = false) String to,
                                               @RequestHeader("Authorization") String sessionToken) throws UserNotExistException, ParseException {
        User currentUser = getCurrentUser(sessionToken);
        List<Bill> bills;
        bills = (from != null && to != null)
                ? billController.getByUserFilterByDate(currentUser, dateConverter(from), dateConverter(to))
                : billController.getByUser(currentUser);
        return (bills.size() > 0) ? ResponseEntity.ok(bills) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /* 4) Consulta de TOP 10 destinos más llamados por el usuario. */
    @GetMapping("/api/lines/top10")
    public ResponseEntity<List<PhoneLine>> getTopTenCalls(@RequestHeader("Authorization") String sessionToken) throws UserNotExistException {
        User currentUser = getCurrentUser(sessionToken);
        List<PhoneLine> topTen = lineController.getTopTen(currentUser);
        return (topTen.size() > 0) ? ResponseEntity.ok(topTen) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /** Employees **/

    /* 2) Manejo de clientes. */
    // realizado en UserWebController

    /* 3) Alta , baja y suspensión de líneas. */
    // realizado en PhoneLineWebController

    /* 4) Consulta de tarifas. */
    @GetMapping("/backoffice/tariffs")
    public ResponseEntity<List<Tariff>> getTariffs(@RequestParam(value = "fromPrefix", required = false) String fromPrefix,
                                                   @RequestParam(value = "toPrefix", required = false) String toPrefix,
                                                   @RequestHeader("Authorization") String sessionToken){
        List<Tariff> tariffList = new ArrayList<>();
        if(fromPrefix != null && toPrefix != null){
            tariffList.add(tariffController.getTariff(Integer.parseInt(fromPrefix + toPrefix)));
        }else {
            tariffList = tariffController.getAll();
        }
        return (tariffList.size() > 0) ? ResponseEntity.ok(tariffList) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /* 5) Consulta de llamadas por usuario. */
    @GetMapping("/backoffice/calls")
    public ResponseEntity<List<Call>> getCallsByUsername(@RequestParam(value = "username", required = false) String username,
                                                         @RequestHeader("Authorization") String sessionToken){
        List<Call> calls;
        calls = username != null ? callController.getByOriginUser(userController.getByUsername(username)) : callController.getAll();
        return (calls.size() > 0) ? ResponseEntity.ok(calls) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Consulta de llamada x id que satisface al getLocation
    @GetMapping("/backoffice/calls/{id}")
    public ResponseEntity<Call> getCall(@PathVariable Integer id, @RequestHeader("Authorization") String sessionToken){
        Call call = callController.getCall(id);
        return (call != null) ? ResponseEntity.ok(call) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /* 6) Consulta de facturación .
     *  La facturación se hará
     *  directamente por un
     *  proceso interno en la
     *  base datos. */

    // Consulta por id
    @GetMapping("/backoffice/bills/{id}")
    public ResponseEntity<Bill> getBill(@PathVariable Integer id, @RequestHeader("Authorization") String sessionToken){
        Bill bill = billController.getBill(id);
        return (bill != null) ? ResponseEntity.ok(bill) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    // Consulta por cliente
    @GetMapping("/backoffice/client/{id}/bills")
    public ResponseEntity<List<Bill>> getBillsByUser(@PathVariable Integer id, @RequestHeader("Authorization") String sessionToken){
        List<Bill> bills;
        bills = billController.getByUser(userController.getUser(id));
        return (bills.size() > 0) ? ResponseEntity.ok(bills) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /** Infrastructure **/

    /* Agregado de llamadas */
    @PostMapping("/inf")
    public ResponseEntity<Call> addCall(@RequestBody AddCallDto dto,
                                        @RequestHeader("Authorization") String sessionToken) throws CallAlreadyExistsException {
        Call call = callController.createCall(dto);
        return ResponseEntity.created(getLocation(call)).build();
    }

    /** Extras **/

    private User getCurrentUser(String sessionToken) throws UserNotExistException {
        return Optional.ofNullable(sessionManager.getCurrentUser(sessionToken)).orElseThrow(UserNotExistException::new);
    }

    private Date dateConverter(String toConvert) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(toConvert);
    }

    private URI getLocation(Call call) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/backoffice/calls/{id}")
                .buildAndExpand(call.getCallId())
                .toUri();
    }
}
