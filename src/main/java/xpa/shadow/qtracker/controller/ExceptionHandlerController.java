package xpa.shadow.qtracker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xpa.shadow.qtracker.exception.NotFoundException;

import java.net.UnknownHostException;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {

    private Map<String, Object> getMap(String message) {
        var response = new LinkedHashMap<String, Object>();
        response.put("timestamp", OffsetDateTime.now());
        response.put("status", 0);
        response.put("error", "");
        response.put("message", message);
        return response;
    }

    private ResponseEntity<Object> getResponse(String message, HttpStatus status) {
        return getResponse(getMap(message), status);
    }

    private ResponseEntity<Object> getResponse(Map<String, Object> response, HttpStatus status) {
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(Exception ex) {
        return getResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnknownHostException.class)
    public ResponseEntity<Object> handleUnknownHostException(Exception ex) {
        return getResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        return getResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
