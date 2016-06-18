package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Repository;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

/**
 * Created by paulina.pislewicz on 2016-06-18.
 */

@Repository
public class FakeEmployeeRepository implements EmployeeRepository {
    @Override
    public void save(Employee employee) {
        System.out.println("save");

    }

    @Override
    public Employee findByEmployeeId(EmployeeId employeeId) {
        System.out.println("findEmployeeById");

        return null;
    }

    @Override
    public boolean isLoginOccupied(String login) {
        System.out.println("login is occupied");

        return false;
    }
}
