package pl.com.bottega.documentmanagement.api;

/**
 * Created by paulina.pislewicz on 2016-07-09.
 */
public class AuthRequiredException extends RuntimeException {
    public AuthRequiredException(){
        super("Authenticated required but no user in current session");
    }
}
