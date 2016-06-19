package pl.com.bottega.documentmanagement.domain;

/**
 * Created by maciuch on 18.06.16.
 */
public interface EmployeeRepository {

    void save(Employee employee);

    Employee findByEmployeeId(EmployeeId employeeId);

    boolean isLoginOccupied(String login);

    Employee findByLoginAndPassword(String login, String hashedPassword);
}
