package pl.com.bottega.documentmanagement.api;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;
import pl.com.bottega.documentmanagement.domain.Role;
import pl.com.bottega.documentmanagement.domain.repositories.RoleRepository;

/**
 * Created by maciuch on 12.06.16.
 */
@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserManager {

    private EmployeeRepository employeeRepository;
    private Employee currentEmployee;
    private RoleRepository roleRepository;
    private EmployeeFactory employeeFactory;
    private PasswordHasher passwordHasher;

//    public UserManager(EmployeeRepository employeeRepository, RoleRepository roleRepository) {
//        this.employeeRepository = employeeRepository;
//        this.roleRepository = roleRepository;
//    }

    public UserManager(EmployeeRepository employeeRepository, EmployeeFactory employeeFactory, PasswordHasher passwordHasher) {
        this.employeeRepository = employeeRepository;
        this.employeeFactory = employeeFactory;
        this.passwordHasher = passwordHasher;
    }

    @Transactional
    public SignupResultDto signup(String login, String password, EmployeeId employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee == null)
            return setupNewAccount(login, password, employeeId);
        else if (employee.isRegistered())
            return failed("employee registered");
        else {
            employee.setupAccount(login, password);
            employeeRepository.save(employee);
            return success();
        }
    }

    private SignupResultDto setupNewAccount(String login, String password, EmployeeId employeeId) {
        if (employeeRepository.isLoginOccupied(login))
            return failed("login is occupied");
        else {
            Employee employee = employeeFactory.create(login, password, employeeId);
//            Employee employee = new Employee(login, hashedPassword(password), employeeId);
            employeeRepository.save(employee);
            return success();
        }
    }

    private SignupResultDto failed(String reason) {
        return new SignupResultDto(reason);
    }

    private SignupResultDto success() {
        return new SignupResultDto();
    }

    private String hashedPassword(String password) {
        return Hashing.sha1().hashString(password, Charsets.UTF_8).toString();
    }

    public SignupResultDto login(String login, String password) {
        this.currentEmployee = employeeRepository.findByLoginAndPassword(login, hashedPassword(password));
        if(this.currentEmployee == null)
            return failed("login or password incorrect");
        else
            return success();
    }

    public Employee currentEmployee() {
        return this.currentEmployee;
    }

    public boolean isAuthenticated(String ...roleNames) {
        return currentEmployee != null && currentEmployee.hasRoles(roleNames);
    }

    @Transactional
    public void setRoles(Long employeeId, String[] roles) {
        Employee employee = employeeRepository.findByEmployeeId(new EmployeeId(employeeId));
        for (String role : roles)
            roleRepository.save(new Role(role));
        employee.setRoles(roles);
        employeeRepository.save(employee);
    }
}
