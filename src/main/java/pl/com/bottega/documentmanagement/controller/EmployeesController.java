package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.documentmanagement.api.SignupResultDto;
import pl.com.bottega.documentmanagement.api.UserManager;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

/**
 * Created by Admin on 03.07.2016.
 */
@RestController
@RequestMapping("/employees")
public class EmployeesController {
    private UserManager userManager;

    public EmployeesController(UserManager userManager) {
        this.userManager = userManager;
    }

    @PutMapping
    public SignupResultDto signup(@RequestBody SignupRequest signupRequest) {
        EmployeeId employeeId = new EmployeeId(signupRequest.getEmployeeId());
       return userManager.signup(signupRequest.getLogin(), signupRequest.getPassword(), employeeId);
    }
}
