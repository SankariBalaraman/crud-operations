package com.example.crudoperations.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    private SQLIntegrityConstraintViolationException ex;

    // Handle duplicate key exceptions (e.g., unique constraint violations)
  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  public ResponseEntity<String> handleDuplicateKeyException(SQLIntegrityConstraintViolationException ex) {
      this.ex = ex;
      String message = "Duplicate entry detected. Please ensure unique fields are not duplicated.";
    return new ResponseEntity<>(message, HttpStatus.CONFLICT); // HTTP 409 Conflict
  }

  // Handle other data integrity violations
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
    String message = "Data integrity error: " + Objects.requireNonNull(ex.getRootCause()).getMessage();
    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST); // HTTP 400 Bad Request
  }

  // Handle SQL connection or syntax errors
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGenericException(Exception ex) {
    if (ex.getCause() instanceof java.sql.SQLException) {
      return new ResponseEntity<>(
              "A database error occurred: " + ex.getMessage(),
              HttpStatus.INTERNAL_SERVER_ERROR
      ); // HTTP 500 Internal Server Error
    }
    return new ResponseEntity<>("An unexpected error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
