package pl.com.bottega.documentmanagement.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.com.bottega.documentmanagement.api.AuthRequiredException;

/**
 * Created by Wojciech Winiarski on 09.07.2016.
 */
@ControllerAdvice //porada dla kontrolera aplikuje sie do wszytskich kontrolerow
public class ErrorsHandler {

    @ExceptionHandler(AuthRequiredException.class)
    public ResponseEntity<String> handleAuthRequiredException(){
      //  MultiValueMap<String, String> m = new LinkedMultiValueMap<>();
       // m.put("Content-Type", Lists.asList("application/json", new  String[]{}));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.CONTENT_TYPE, "application/json");

        return new ResponseEntity<String>("{'Error': 'authentication_required'}",
                httpHeaders, HttpStatus.UNAUTHORIZED);
    }
}
