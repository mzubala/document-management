package pl.com.bottega.documentmanagement.api;

import org.springframework.stereotype.Service;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

/**
 * Created by Dell on 2016-07-31.
 */
@Service
public class EmployeeFactory {

    private static final String INITIAL_ROLE = "STAFF";

    private PasswordHasher passwordHasher;

    public EmployeeFactory(PasswordHasher passwordHasher) {
        this.passwordHasher = passwordHasher;
    }

    public Employee create(String login, String password, EmployeeId employeeId) {
        Employee employee = new Employee(login, password, employeeId);
//        employee.updateRoles(Sets.newHashSet(INITIAL_ROLE));
        return employee;
    }
}
