package pl.com.bottega.documentmanagement.api;

/**
 * Created by Admin on 09.07.2016.
 */
public class AuthRequireException extends RuntimeException{
    public AuthRequireException(){
        super("No user is current session");
    }
}
