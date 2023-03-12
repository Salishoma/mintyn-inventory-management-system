package org.mintyn.inventory.response.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(value = {ApiBadRequestException.class})
    public ResponseEntity<ApiException> handleApiBadRequestException(ApiBadRequestException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(e.getMessage(),
                false, badRequest);

        return new ResponseEntity<>(apiException, badRequest);
    }

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ExceptionHandler(value = {ApiResourceNotFoundException.class})
    public ResponseEntity<ApiException> handleApiResourceNotFoundException(ApiResourceNotFoundException e) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        System.out.println("=======>checking advice: code is " + notFound);

        ApiException apiException = new ApiException(e.getMessage(),
                false, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(apiException, notFound);
    }
}
