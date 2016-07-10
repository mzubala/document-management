package pl.com.bottega.documentmanagement.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.com.bottega.documentmanagement.api.AuthRequiredException;

/**
 * Created by paulina.pislewicz on 2016-07-09.
 */
@ControllerAdvice
public class ErrorsHandler {
    @ExceptionHandler(AuthRequiredException.class)
    public ResponseEntity<String> handleAuthRequiredException(){
        MultiValueMap<String, String> m = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<String>(
                "{'error': 'authentication_required'}",
                headers,
                HttpStatus.UNAUTHORIZED
        );
    }
}
