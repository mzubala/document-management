package pl.com.bottega.documentmanagement.domain;

/**
 * Created by Dell on 2016-06-18.
 */
public interface EmployeeRepository {

    void save(Employee employee);

    Employee findByEmployeeId(EmployeeId employeeId);

    boolean isLoginOccupied(String login);
}
