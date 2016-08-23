package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.SignupResultDto;
import pl.com.bottega.documentmanagement.api.UserManager;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

/**
 * Created by bartosz.paszkowski on 03.07.2016.
 */
@RestController
@RequestMapping("/employees") //wszystkie metody zostana zmapowane
public class EmployeesController {

    private UserManager userManager;

    public EmployeesController(UserManager userManager){
        this.userManager = userManager;
    }

    @PutMapping // zadanie z metodą PUT - mapowanie
    public SignupResultDto signup(@RequestBody SignupRequest signupRequest){
        EmployeeId employeeId = new EmployeeId(signupRequest.getEmployeeId());
        return userManager.signup(signupRequest.getLogin(), signupRequest.getPassword(),employeeId);
    }

    @PostMapping("/{employeeId}/roles")
    public void updateRoles(@PathVariable("employeeId") Long id, @RequestBody UpdateRolesRequest request) {
        EmployeeId employeeId = new EmployeeId(id);
        userManager.updateRoles(employeeId, request.getRoles());
    }

}
