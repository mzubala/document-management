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
import pl.com.bottega.documentmanagement.domain.EmployeeRepository;

/**
 * Created by Wojciech Winiarski on 12.06.16.
 */
@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserManager {

    private PasswordHasher passwordHasher;
    private EmployeeRepository employeeRepository;
    private Employee currentEmployee;

    private EmployeeFactory employeeFactory;



    public UserManager(EmployeeRepository employeeRepository, EmployeeFactory employeeFactory, PasswordHasher passwordHasher){
        this.employeeRepository = employeeRepository;
        this.employeeFactory = employeeFactory;
        this.passwordHasher = passwordHasher;
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ) //poziom izolacji aby nie dalo sie utworzyc 2 userow z takim samym loginem
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
            Employee employee = employeeFactory.create(login, password, employeeId);
            employeeRepository.save(employee);
            return success();
        }
    }



    public SignupResultDto login(String login, String password){ //wpisuje sie nie zahashowane haslo wiec trzeba hashowac

        this.currentEmployee =
                employeeRepository.findByLoginAndPassword(login, passwordHasher.hashedPassword(password));
        if(this.currentEmployee == null)
            return failed("login or password incorrect");
        else return success();
    }

    public Employee currentEmployee() {
        return this.currentEmployee;

    }

    public boolean isAuthenticated(String ...roleName) {

        return currentEmployee != null && currentEmployee.hasRoles(roleName);

    }
}
