package pl.com.bottega.documentmanagement.domain.repositories;

import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.Role;

import java.util.Collection;
import java.util.Set;

/**
 * Created by paulina.pislewicz on 2016-06-18.
 */
public interface EmployeeRepository {
    void save(Employee employee);
    Employee findByEmployeeId(EmployeeId employeeId);

    boolean isLoginOccupied(String login);

    Employee findByLoginAndPassword(String login, String hashedPassword);

    Collection<Role> getRoles(Set<String> roleNames);



}
