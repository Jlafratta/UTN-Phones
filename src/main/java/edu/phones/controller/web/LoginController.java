package edu.phones.controller.web;

import edu.phones.controller.UserController;
import edu.phones.domain.User;
import edu.phones.dto.LoginRequestDto;
import edu.phones.exceptions.InvalidLoginException;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.exceptions.ValidationException;
import edu.phones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class LoginController {

    UserController userController;
    SessionManager sessionManager;

    @Autowired
    public LoginController(UserController userController, SessionManager sessionManager) {
        this.userController = userController;
        this.sessionManager = sessionManager;
    }

    /**
     *  Si quiero devolver el token en el body de la response, ademas de en el header,
     *  tengo que castear el ResponseEntity (tipo de dato del metodo) a <String>
     *  y declarar la response de la siguiente manera:
     *  response = ResponseEntity.ok().headers(createHeaders(token)).body(token);
     */
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDto loginRequestDto) throws InvalidLoginException, ValidationException {
        ResponseEntity response;
        try{
            
            User u = userController.login(loginRequestDto.getUsername(), loginRequestDto.getPassword()); //Loguea con la data del dto
            String token = sessionManager.createSession(u);         //Genera el token con su session
            response = ResponseEntity.ok().headers(createHeaders(token)).build();   //Crea el header con el token
        }catch (UserNotExistException e){
            throw  new InvalidLoginException(e);
        }
        return response;
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String token){
        sessionManager.removeSession(token);
        return ResponseEntity.ok().build();
    }

    // Crea el Header de Authorization con el token que le corresponde en la session
    private HttpHeaders createHeaders(String token){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        return responseHeaders;
    }

}
