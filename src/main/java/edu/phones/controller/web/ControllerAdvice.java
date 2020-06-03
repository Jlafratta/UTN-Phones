package edu.phones.controller.web;

import edu.phones.dto.ErrorResponseDto;
import edu.phones.exceptions.InvalidLoginException;
import edu.phones.exceptions.alreadyExist.*;
import edu.phones.exceptions.notExist.*;
import edu.phones.exceptions.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *  Filtro que maneja las excepciones que se lanzan en los rest controller,
 *  les asigna un http status y devuelve un mensaje de error (opcional)
 **/

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidLoginException.class)
    public ErrorResponseDto handleLoginException(InvalidLoginException exc) {
        return new ErrorResponseDto(1, "Invalid login");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponseDto handleValidationException(ValidationException exc) {
        return new ErrorResponseDto(2, exc.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotExistException.class)
    public ErrorResponseDto handleUserNotExist() {
        return new ErrorResponseDto(3, "User not exists");
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProfileNotExistException.class)
    public ErrorResponseDto handleProfileNotExist(){
        return new ErrorResponseDto(5, "Profile not exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TariffNotExistException.class)
    public ErrorResponseDto handleTariffNotExist(){
        return new ErrorResponseDto(5, "Tariff not exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CityNotExistException.class)
    public ErrorResponseDto handleCityNotExist(){
        return new ErrorResponseDto(6, "City not exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProvinceNotExistException.class)
    public ErrorResponseDto handleProvinceNotExist(){
        return new ErrorResponseDto(6, "Pronvince not exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProfileAlreadyExistException.class)
    public ErrorResponseDto handleProfileAlreadyExist(){
        return new ErrorResponseDto(7, "Profile already exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PhoneLineAlreadyExistsException.class)
    public ErrorResponseDto handlePhoneLineAlreadyExist(){
        return new ErrorResponseDto(8, "PhoneLine already exists");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorResponseDto handleUserAlreadyExistException(){
        return new ErrorResponseDto(4, "User already exist");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ProvinceAlreadyExistsException.class)
    public ErrorResponseDto handleProvinceAlreadyExistException(){
        return new ErrorResponseDto(4, "Province already exist");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CityAlreadyExistException.class)
    public ErrorResponseDto handleCityAlreadyExistException() {
        return new ErrorResponseDto(4, "City already exist");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TarriffAlreadyExistsException.class)
        public ErrorResponseDto handleTariffAlreadyExist(){
        return new ErrorResponseDto(7, "Tariff already  exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PhoneLineNotExistException.class)
    public ErrorResponseDto handlePhoneLineNotExist(){
        return new ErrorResponseDto(9, "PhoneLine not exists");
    }




    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TypeNotExistException.class)
    public ErrorResponseDto handleUserTypeNotExist(){
        return new ErrorResponseDto(10, "UserType not exists");
    }

}
