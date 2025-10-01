package ana.learning.padel.padelBooking.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", "No se puede eliminar el recurso porque existen reservas activas.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error); // 409 Conflict
    }

    @ExceptionHandler(PastDateException.class)
    public ResponseEntity<Map<String, String>> handlePastDate(PastDateException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", "No se puede crear un calendario en una fecha pasada.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error); // 400 Bad Request
    }


}