package com.abel.countriesapi.exception;

import com.abel.countriesapi.dto.response.AppResponse;
import com.abel.countriesapi.exception.model.ErrorDetails;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)//Gives this component the highest precedence among other error handlers!
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ClassCastException.class)
    public final ResponseEntity<AppResponse<ErrorDetails>> handlePackageNotFoundException(ClassCastException ex, WebRequest request) {

        ErrorDetails errorDetails =
                new ErrorDetails(new Date(), ex.getMessage(),
                        request.getDescription(true), HttpStatus.INTERNAL_SERVER_ERROR);

        return ResponseEntity.status(errorDetails.getCode()).body(AppResponse.<ErrorDetails>builder()
                .message(ex.getMessage())
                .status(errorDetails.getCode().name())
                .error(errorDetails)
                .build());
    }
}
