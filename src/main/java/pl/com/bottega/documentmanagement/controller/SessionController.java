package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.documentmanagement.api.SignupResultDto;
import pl.com.bottega.documentmanagement.api.UserManager;

/**
 * Created by Dell on 2016-07-03.
 */
@RestController
@RequestMapping("/session")
public class SessionController {

    private UserManager userManager;

    public SessionController(UserManager userManager) {
        this.userManager = userManager;
    }

    @PutMapping
    public SignupResultDto login(@RequestBody LoginRequest loginRequest) {
        return userManager.login(loginRequest.getLogin(), loginRequest.getPassword());
    }
}
