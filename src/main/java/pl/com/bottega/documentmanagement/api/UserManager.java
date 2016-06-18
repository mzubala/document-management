package pl.com.bottega.documentmanagement.api;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

/**
 * Created by maciuch on 12.06.16.
 */
@Service
public class UserManager {

    private EmployeeRepository employeeRepository;

    @Autowired
    public UserManager(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }


    public SignupResultDto signup(String login, String password, EmployeeId employeeId)  {
        Employee employee =  employeeRepository.findByEmployeeId(employeeId);
        if (employee == null)
            return setupNewAccount(login, password, employeeId);
        else if (employee.isRegistered())
           return failed("emloyee registered");
        else {
            employee.setupAccount(login,password);
            employeeRepository.save(employee);
            return success();
        }
    }

    public void login(String login, String password){

    }

    public Employee currentEmployee() {
        return null;
    }

    private SignupResultDto setupNewAccount(String login, String password, EmployeeId employeeId) {
        if (employeeRepository.isLoginOccupied(login))
            return failed("login is occupied");
        else {

            Employee employee = new Employee(login, hashedPassword(password), employeeId);
            employeeRepository.save(employee);
            return success();
        }
    }

    private String hashedPassword(String password){
        return Hashing.sha1().hashString(password, Charsets.UTF_8).toString();
    }

    private SignupResultDto failed(String reason){
        return  new SignupResultDto(reason);
    }

    private SignupResultDto success(){
        return  new SignupResultDto();
    }

}
