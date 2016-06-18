package pl.com.bottega.documentmanagement.domain;

/**
 * Created by Wojciech Winiarski on 18.06.2016.
 */
public interface EmployeeRepository {


   void save(Employee employee);

    Employee findByEmployee(EmployeeId employeeId);

    boolean isLoginOccupied(String login);

}
