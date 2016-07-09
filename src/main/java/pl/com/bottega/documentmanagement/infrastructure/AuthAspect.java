package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.AuthRequiredException;
import pl.com.bottega.documentmanagement.api.UserManager;

/**
 * Created by Dell on 2016-07-09.
 */
@Component
public class AuthAspect {

    private UserManager userManager;

    public AuthAspect(UserManager userManager) {
        this.userManager = userManager;
    }

    public void checkAuth() {
        if (!userManager.isAuthenticated())
            throw new AuthRequiredException();
    }
}
