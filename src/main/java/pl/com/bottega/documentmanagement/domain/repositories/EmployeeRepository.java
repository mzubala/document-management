package pl.com.bottega.documentmanagement.domain.repositories;

import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.Role;

import java.util.Collection;
import java.util.Set;

/**
 * Created by maciuch on 18.06.16.
 */
public interface EmployeeRepository {

    void save(Employee employee);

    Employee findByEmployeeId(EmployeeId employeeId);

    boolean isLoginOccupied(String login);

    Employee findByLoginAndPassword(String login, String hashedPassword);

    Collection<Role> getRoles(Set<String> roleNames);

    Collection<Employee> findByEmployeeIds(Set<EmployeeId> ids);
}
