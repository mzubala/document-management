package pl.com.bottega.documentmanagement.api;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.Role;

/**
 * Created by paulina.pislewicz on 2016-07-31.
 */
@Service
public class EmployeeFactory {
    private static final String INITIAL_ROLE = "STAFF";
    private PasswordHassher passwordHassher;

    public EmployeeFactory(PasswordHassher passwordHassher) {
        this.passwordHassher = passwordHassher;
    }

    public Employee create(String login, String password, EmployeeId employeeId){
        Employee employee = new Employee(login, passwordHassher.hashedPassword(password), employeeId);
        employee.updateRoles(Sets.newHashSet(new Role(INITIAL_ROLE)));
        return employee;
    }
}
