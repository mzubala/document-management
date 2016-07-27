package pl.com.bottega.documentmanagement.api;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.Role;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import java.util.Collection;

/**
 * Created by maciuch on 12.06.16.
 */

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)

public class UserManager {

    private EmployeeRepository employeeRepository;
    private Employee currentEmployee;

    @Autowired
    public UserManager (EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }
    @Transactional (isolation = Isolation.REPEATABLE_READ)
    public SignupResultDto signup(String login, String password, EmployeeId employeeId){
       Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee == null)
            return setupNewAccount(login, password, employeeId);
        else if (employee.isRegistered())
            return failed("Employee already registered");
        else {
            employee.setupAccount (login, password);
            employeeRepository.save(employee);
            return success();
        }
    }

    private SignupResultDto setupNewAccount(String login, String password, EmployeeId employeeId) {
        if(employeeRepository.isLoginOccupied(login))
            return failed("Login is ocupied!");
        else{
            Employee employee = new Employee(login, hashedPassword(password), employeeId);
            employeeRepository.save(employee);
            return success();
        }
    }

    private SignupResultDto failed(String reason){
        return new SignupResultDto(reason);
    }

    private SignupResultDto success (){
        return new SignupResultDto();
    }

    private String hashedPassword(String password) {
        return Hashing.sha1().hashString(password, Charsets.UTF_8).toString();
    }

    @Transactional
    public SignupResultDto login(String login, String password){
       this.currentEmployee = employeeRepository.findByLoginAndPassword(login, hashedPassword(password));
        if (this.currentEmployee == null)
            return failed ("login or password incorrect");
        return success();

    }

    public Employee currentEmployee() {
        return this.currentEmployee;
    }

    public boolean isAuthenticated(String... roleNames) {
        if (currentEmployee ==null)
            return false;
        return currentEmployee.hasRoles(roleNames);
    }

    public void addRole(Long employeeId, Collection<Role> role) {
        Employee employee = employeeRepository.findByEmployeeId(new EmployeeId(employeeId));

        if (role != null){
            employee.replaceRoles(role);
        }
        employeeRepository.save(employee);

    }
}
