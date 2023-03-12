package org.mintyn.inventory.response.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiException {

    private String message;

    private boolean success;

    private HttpStatus httpStatus;
}