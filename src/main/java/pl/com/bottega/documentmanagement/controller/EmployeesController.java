package pl.com.bottega.documentmanagement.controller;

import org.springframework.web.bind.annotation.*;
import pl.com.bottega.documentmanagement.api.SignupResultDto;
import pl.com.bottega.documentmanagement.api.UserManager;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

/**
 * Created by paulina.pislewicz on 2016-07-03.
 */
@RestController
@RequestMapping("/employees")
public class  EmployeesController {
    private UserManager userManager;

    public EmployeesController (UserManager userManager){
        this.userManager=userManager;
    }

    @PutMapping
    public SignupResultDto signup(@RequestBody SignupRequest signupRequest){
        EmployeeId employeeId = new EmployeeId(signupRequest.getEmployeeId());
        return userManager.signup(signupRequest.getLogin(), signupRequest.getPassword(), employeeId);
    }
    @PutMapping ("/{employeeId}/roles")
    public void updateRoles(@PathVariable Long id, @RequestBody UpdateRolesRequest request){
        EmployeeId employeeId = new EmployeeId(id);
        userManager.updateRoles(employeeId, request.getRoles());

    }

    }
