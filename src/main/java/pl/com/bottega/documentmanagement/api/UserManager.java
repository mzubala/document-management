package pl.com.bottega.documentmanagement.api;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.EmployeeRepository;

/**
 * Created by Wojciech Winiarski on 12.06.16.
 */
@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserManager {
    private EmployeeRepository employeeRepository;
    private Employee currentEmployee;

    @Autowired
    public UserManager(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }


    @Transactional
    public SignupResultDto signup(String login, String password, EmployeeId employeeId){

      Employee employee = employeeRepository.findByEmployee(employeeId);
      if(employee == null)
          return setupNewAccount(login, password, employeeId);
        else if(employee.isRegistered())
          return failed("Employee registered");
        else {
          employee.setupAccount(login, password);
          employeeRepository.save(employee);
          return success();
      }

    }

    private SignupResultDto failed(String reason) {
        return new SignupResultDto(reason);

    }
    private SignupResultDto success(){
        return new SignupResultDto();
    }

    private SignupResultDto setupNewAccount(String login, String password, EmployeeId employeeId){
        if(employeeRepository.isLoginOccupied(login))
            return failed("Login is occupied");
        else {
            Employee employee = new Employee(login, hashedPassword(password), employeeId);
            employeeRepository.save(employee);
            return success();
        }
    }
    private String hashedPassword(String password){
        return Hashing.sha1().hashString(password, Charsets.UTF_8).toString();
    }

    public SignupResultDto login(String login, String password){ //wpisuje sie nie zahashowane haslo wiec trzeba hashowac

        this.currentEmployee =
                employeeRepository.findByLoginAndPassword(login, hashedPassword(password));
        if(this.currentEmployee == null)
            return failed("login or password incorect");
        else return success();
    }


    public Employee currentEmployee() {
        return this.currentEmployee;

    }

}
