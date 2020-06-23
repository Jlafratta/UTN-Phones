package edu.phones.controller.web;


import edu.phones.controller.TariffController;
import edu.phones.domain.City;
import edu.phones.domain.Tariff;
import edu.phones.domain.User;
import edu.phones.domain.UserProfile;
import edu.phones.dto.AddUserDto;
import edu.phones.dto.UserDto;
import edu.phones.exceptions.alreadyExist.TarriffAlreadyExistsException;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import edu.phones.exceptions.notExist.CityNotExistException;
import edu.phones.exceptions.notExist.ProfileNotExistException;
import edu.phones.exceptions.notExist.TariffNotExistException;
import edu.phones.exceptions.notExist.UserNotExistException;
import edu.phones.session.Session;
import edu.phones.session.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tariff")
public class TariffWebController {

    SessionManager sessionManager;
    TariffController tariffController;

    @Autowired
    public TariffWebController(SessionManager sessionManager, TariffController tariffController) {
        this.sessionManager = sessionManager;
        this.tariffController = tariffController;
    }

    @GetMapping("/{tariff_key}")
     public ResponseEntity<Tariff> getTariff(@PathVariable Integer tariff_key, @RequestHeader("Authorization") String sessionToken)
    {
        Tariff tariff = tariffController.getTariff(tariff_key);

        return (tariff!=null) ? ResponseEntity.ok(tariff) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<List<Tariff>> getTariffs(@RequestHeader("Authorization") String sessionToken){
        List<Tariff> tariffs = new ArrayList<>();
        tariffs = tariffController.getAll();
        return (tariffs.size() > 0) ? ResponseEntity.ok(tariffs) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PostMapping
    public ResponseEntity<Tariff> addTariff(@RequestBody Tariff tariff, @RequestHeader("Authorization") String sessionToken) throws TarriffAlreadyExistsException {
        Tariff toAdd;

        toAdd = tariffController.createTariff(tariff);

        return ResponseEntity.created(getLocation(toAdd)).build();
    }

    @DeleteMapping
    public ResponseEntity<Tariff> removeTariff(@RequestBody Tariff tariff, @RequestHeader("Authorization") String sessionToken) throws TariffNotExistException {

        Tariff toRemove;


        toRemove = tariff;
        tariffController.remove(toRemove);

        return ResponseEntity.ok(toRemove);
    }

    private URI getLocation(Tariff newTariff) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{tariff_key}")
                .buildAndExpand(newTariff.getKey())
                .toUri();
    }
}
