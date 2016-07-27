package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
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
        return userManager.signup(signupRequest.getLogin(), signupRequest.getPassword(), new EmployeeId(signupRequest.getEmployeeId()));
    }

    @PutMapping("/{employeeId}/roles")
    public void changeRoles(@PathVariable Long employeeId, @RequestBody RoleRequest roles) {
        userManager.setRoles(roles.getRoles(), new EmployeeId(employeeId));
    }
}
