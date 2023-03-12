package org.mintyn.inventory.response.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApiBadRequestException extends RuntimeException{

    public ApiBadRequestException(String message) {
        super(message);
    }

}
