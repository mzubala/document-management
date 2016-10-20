package pl.com.bottega.documentmanagement.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.com.bottega.documentmanagement.api.AuthRequiredException;

/**
 * Created by Dell on 2016-07-09.
 */
@ControllerAdvice
public class ErrorsHandler {

    @ExceptionHandler(AuthRequiredException.class)
    public ResponseEntity<String> handlerAuthRequiredException() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");


        return new ResponseEntity<String>(
                "{'error': 'authentication required'}",
                headers,
                HttpStatus.UNAUTHORIZED);
    }
}
