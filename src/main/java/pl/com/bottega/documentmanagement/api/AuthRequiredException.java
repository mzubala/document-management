package pl.com.bottega.documentmanagement.api;

/**
 * Created by Dell on 2016-07-09.
 */
public class AuthRequiredException extends RuntimeException {

    public AuthRequiredException() {
        super("Authentication required but no user in current session");
    }
}
