package com.controller;

import com.internal.model.Response;
import com.utils.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.format.DateTimeParseException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
    extends ResponseEntityExceptionHandler {

  @ExceptionHandler(value
      = { DateTimeParseException.class })
  protected ResponseEntity<Object> handleConflict(
      RuntimeException ex, WebRequest request) {
    String bodyOfResponse = "This should be application specific";
    return new ResponseEntity<>(new Response().builder()
        .errorMessage(String.format("Reservation should be within next day and %s days", Constants.RESERVATION_OPEN_FOR_NEXT_DAYS))
        .build(), HttpStatus.OK);
  }
}
