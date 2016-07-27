package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.AuthRequiredException;
import pl.com.bottega.documentmanagement.api.RequiresAuth;
import pl.com.bottega.documentmanagement.api.UserManager;

/**
 * Created by maciuch on 09.07.16.
 */
@Component
public class AuthAspect {

    private UserManager userManager;

    public AuthAspect(UserManager userManager) {
        this.userManager = userManager;
    }

    public void checkAuth(RequiresAuth requiresAuth) {
        if (!userManager.isAuthenticated(requiresAuth.roles()))
            throw new AuthRequiredException();
    }

}
