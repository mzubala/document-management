package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.AuthRequireException;
import pl.com.bottega.documentmanagement.api.RequiresAuth;
import pl.com.bottega.documentmanagement.api.UserManager;

import javax.naming.AuthenticationException;

/**
 * Created by Admin on 09.07.2016.
 */
@Component
public class AuthAspect {
    private UserManager userManager;

    public AuthAspect(UserManager userManager){
        this.userManager = userManager;
    }

    public void checkAuth(RequiresAuth requiresAuth) {
        if (!userManager.isAuthenticated()) {
            throw new AuthRequireException();
        }
    }
}
