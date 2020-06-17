package edu.phones.controller.web;

import edu.phones.controller.PhoneLineController;
import edu.phones.controller.UserController;
import edu.phones.controller.UserTypeController;
import edu.phones.domain.PhoneLine;
import edu.phones.domain.User;
import edu.phones.domain.UserType;
import edu.phones.dto.AddLineDto;
import edu.phones.dto.LineDto;
import edu.phones.dto.StateDto;
import edu.phones.exceptions.alreadyExist.PhoneLineAlreadyExistsException;
import edu.phones.exceptions.notExist.PhoneLineNotExistException;
import edu.phones.exceptions.notExist.TypeNotExistException;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.session.SessionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/backoffice/lines")
public class PhoneLineWebController {

    PhoneLineController lineController;
    UserController userController;
    UserTypeController typeController;
    SessionManager sessionManager;

    public PhoneLineWebController(PhoneLineController lineController, UserController userController, UserTypeController typeController, SessionManager sessionManager) {
        this.lineController = lineController;
        this.userController = userController;
        this.typeController = typeController;
        this.sessionManager = sessionManager;
    }

    /* 3) Alta , baja y suspensión de líneas. */

    @PatchMapping("/{id}")
    public ResponseEntity<PhoneLine> changeState(@PathVariable Integer id, @RequestBody StateDto stateDto, @RequestHeader("Authorization") String sessionToken) throws PhoneLineNotExistException {

        PhoneLine toChange = lineController.getLine(id);
        Optional.ofNullable(toChange).orElseThrow(PhoneLineNotExistException::new);

        if (toChange.getState() == stateDto.isState()) { return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build(); }

        toChange.setState(stateDto.isState());
        toChange = lineController.updateLine(toChange);

        return ResponseEntity.ok(toChange);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhoneLine> getLine(@PathVariable Integer id, @RequestHeader("Authorization") String sessionToken){

        PhoneLine line = lineController.getLine(id);

        return (line != null) ? ResponseEntity.ok(line) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping
    public ResponseEntity<List<PhoneLine>> getLines(@RequestHeader("Authorization") String sessionToken){

        List<PhoneLine> lines;
        lines = lineController.getAll();

        return (lines.size() > 0) ? ResponseEntity.ok(lines) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    public ResponseEntity<PhoneLine> addLine(@RequestBody AddLineDto lineDto, @RequestHeader("Authorization") String sessionToken) throws PhoneLineAlreadyExistsException, UserNotExistException, TypeNotExistException {

        PhoneLine line;
        User user = userController.getUser(lineDto.getUserId());
        UserType type = typeController.getType(lineDto.getTypeId());

        Optional.ofNullable(user).orElseThrow(UserNotExistException::new);
        Optional.ofNullable(type).orElseThrow(TypeNotExistException::new);

        line = new PhoneLine(lineDto.getNumber(), lineDto.getState(), user, type);
        line = lineController.createLine(line);

        return ResponseEntity.created(getLocation(line)).build();
    }

    @PutMapping
    public ResponseEntity<PhoneLine> updateLine(@RequestBody LineDto lineDto, @RequestHeader("Authorization") String sessionToken) throws PhoneLineNotExistException, UserNotExistException, TypeNotExistException {

        PhoneLine toUpdate;
        User user = userController.getUser(lineDto.getUserId());
        UserType type = typeController.getType(lineDto.getTypeId());

        Optional.ofNullable(user).orElseThrow(UserNotExistException::new);
        Optional.ofNullable(type).orElseThrow(TypeNotExistException::new);

        toUpdate = new PhoneLine(lineDto.getLineId(), lineDto.getNumber(), lineDto.getState(), user, type);
        toUpdate = lineController.updateLine(toUpdate);

        return ResponseEntity.ok(toUpdate);
    }

    @DeleteMapping
    public ResponseEntity<PhoneLine> removeLine(@RequestBody LineDto lineDto, @RequestHeader("Authorization") String sessionToken) throws PhoneLineNotExistException, UserNotExistException, TypeNotExistException {

        PhoneLine toRemove;
        User user = userController.getUser(lineDto.getUserId());
        UserType type = typeController.getType(lineDto.getTypeId());

        Optional.ofNullable(user).orElseThrow(UserNotExistException::new);
        Optional.ofNullable(type).orElseThrow(TypeNotExistException::new);

        toRemove = new PhoneLine(lineDto.getLineId(), lineDto.getNumber(), lineDto.getState(), user, type);
        lineController.removeLine(toRemove);

        return ResponseEntity.ok(toRemove);
    }

    private URI getLocation(PhoneLine line) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(line.getpLineId())
                .toUri();
    }

}
