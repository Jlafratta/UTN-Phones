package edu.phones.controller.web;

import edu.phones.controller.CityController;
import edu.phones.controller.ProvinceController;
import edu.phones.controller.UserController;
import edu.phones.controller.UserProfileController;
import edu.phones.domain.City;
import edu.phones.domain.Province;
import edu.phones.domain.User;
import edu.phones.domain.UserProfile;
import edu.phones.dto.AddCityDto;
import edu.phones.dto.AddUserDto;
import edu.phones.dto.CityDto;
import edu.phones.dto.UserDto;
import edu.phones.exceptions.alreadyExist.CityAlreadyExistException;
import edu.phones.exceptions.alreadyExist.UserAlreadyExistsException;
import edu.phones.exceptions.notExist.CityNotExistException;
import edu.phones.exceptions.notExist.ProfileNotExistException;
import edu.phones.exceptions.notExist.ProvinceNotExistException;
import edu.phones.exceptions.notExist.UserNotExistException;
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
@RequestMapping("/api/city")
public class CityWebController {

    CityController cityController;
    SessionManager sessionManager;
    ProvinceController provinceController;

    @Autowired
    public CityWebController(CityController cityController, SessionManager sessionManager) {
        this.cityController = cityController;
        this.sessionManager = sessionManager;
    }


    @GetMapping("/{id}")
    public ResponseEntity<City> getCity(@PathVariable Integer id, @RequestHeader("Authorization") String sessionToken){

        City city = cityController.getCity(id);

        return (city != null) ? ResponseEntity.ok(city) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping
    public ResponseEntity<List<City>> getCities(@RequestHeader("Authorization") String sessionToken){
        List<City> cities = new ArrayList<>();
        cities = cityController.getAll();
        return (cities.size() > 0) ? ResponseEntity.ok(cities) : ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping
    public ResponseEntity<City> addCity(@RequestBody AddCityDto cityDto, @RequestHeader("Authorization") String sessionToken) throws CityAlreadyExistException, ProvinceNotExistException {

        City toAdd;

        Province province = provinceController.getProvince(cityDto.getProvinceId());


        Optional.ofNullable(province).orElseThrow(ProvinceNotExistException::new);

        toAdd = new City (cityDto.getPrefix(),cityDto.getName(),province);
        toAdd = cityController.createCity(toAdd);


        return ResponseEntity.created(getLocation(toAdd)).build();
    }

    @PutMapping
    public ResponseEntity<City> updateCity(@RequestBody CityDto cityDto, @RequestHeader("Authorization") String sessionToken) throws CityNotExistException, ProvinceNotExistException  {

        City toUpdate;

        Province province= provinceController.getProvince(cityDto.getProvinceId());

        Optional.ofNullable(province).orElseThrow(ProvinceNotExistException::new);

        toUpdate = new City(cityDto.getId(),cityDto.getPrefix(),cityDto.getName(),province);

        return ResponseEntity.ok(toUpdate);
    }

    @DeleteMapping
    public ResponseEntity<City> removeCity(@RequestBody CityDto cityDto, @RequestHeader("Authorization") String sessionToken) throws  CityNotExistException,ProvinceNotExistException {

        City toRemove;

        Province province = provinceController.getProvince(cityDto.getProvinceId());

        Optional.ofNullable(province).orElseThrow(ProvinceNotExistException::new);

        toRemove = new City (cityDto.getId(),cityDto.getPrefix(),cityDto.getName(),province);

        return ResponseEntity.ok(toRemove);
    }

    private URI getLocation(City newCity) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCity.getCityId())
                .toUri();
    }


}
