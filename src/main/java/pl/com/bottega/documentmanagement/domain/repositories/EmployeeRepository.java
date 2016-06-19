package pl.com.bottega.documentmanagement.domain.repositories;

import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

/**
 * Created by bartosz.paszkowski on 18.06.2016.
 */
public interface EmployeeRepository {
    public void save(Employee employee);

    public Employee findByEmployeeId(EmployeeId employeeId);

    boolean isLoginOccupied(String login);

    Employee findByLoginAndPassword(String login, String hashedPassword);
}
