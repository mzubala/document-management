package pl.com.bottega.documentmanagement.api;

import com.google.common.collect.Sets;
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
import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by maciuch on 12.06.16.
 */
@Service
@Scope (value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS) //!!!!!!
public class UserManager {

    private EmployeeRepository employeeRepository;
    private Employee currentEmployee;
    private EmployeeFactory employeeFactory;
    private PasswordHasher passwordHasher;

    @Autowired
    public UserManager(EmployeeRepository employeeRepository, EmployeeFactory employeeFactory, PasswordHasher passwordHasher){
        this.employeeRepository = employeeRepository;
        this.employeeFactory = employeeFactory;
        this.passwordHasher = passwordHasher;
    }

    @Transactional (isolation = Isolation.REPEATABLE_READ)//przed rozpoczęciem metody jest otwierana tranzakcja po zakończeniu metody tranzakcja jest zatwierdzana
    public SignupResultDto signup(String login, String password, EmployeeId employeeId)  {
        Employee employee =  employeeRepository.findByEmployeeId(employeeId);
        if (employee == null)
            return setupNewAccount(login, password, employeeId);
        if (employee.isRegistered())
            return failed("employee registered");
        if (employeeRepository.isLoginOccupied(login))
            return failed("login occupied");
        employee.setupAccount(login, password);
        employeeRepository.save(employee);
        return success();
        }

    public SignupResultDto signup(EmployeeId employeeId){
        checkNotNull(employeeId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee != null)
            return failed("Employee registered");

        employee = employeeFactory.create(null,null,employeeId);
        employeeRepository.save(employee);
        return success();
    }


    public SignupResultDto login (String login, String password){
        this.currentEmployee = employeeRepository.findByLoginAndPassword(login, passwordHasher.hashedPassword(password));
        if (currentEmployee==null)
            return failed("Login or pass incorrect");
        else
            return success();

    }

    public Employee currentEmployee() {
        return this.currentEmployee;
    }

    private SignupResultDto setupNewAccount(String login, String password, EmployeeId employeeId) {
        if (employeeRepository.isLoginOccupied(login))
            return failed("login is occupied");
        else {
            //Employee employee = new Employee(login, hashedPassword(password), employeeId);
            Employee employee = employeeFactory.create(login, password, employeeId);
            employeeRepository.save(employee);
            return success();
        }
    }


    private SignupResultDto failed(String reason){
        return  new SignupResultDto(reason);
    }

    private SignupResultDto success(){
        return  new SignupResultDto();
    }


    public boolean isAuthenticated(String ...roleNames) {
        return currentEmployee != null && currentEmployee.hasRoles(roleNames);
    }
    @Transactional
    @RequiresAuth(roles = "ADMIN")
    public void updateRoles(EmployeeId employeeId, Set<String> roleNames) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        employee.updateRoles(getRoles(roleNames));
    }
    @Transactional
    public void createAdmin(){
        Employee employee = new Employee("admin", passwordHasher.hashedPassword("admin"), new EmployeeId(0L));
        employee.updateRoles(getRoles(Sets.newHashSet("ADMIN")));
        employeeRepository.save(employee);
    }
    private Set<Role> getRoles(String... roleNames) {
        return getRoles(Sets.newHashSet(roleNames));
    }

    private Set<Role> getRoles(Set<String> roleNames) {
        Set<Role> rolesToUpdate = new HashSet<>();
        Collection<Role> existingRoles = employeeRepository.getRoles(roleNames);
        rolesToUpdate.addAll(existingRoles);
        for (String roleName : roleNames) {
            Role role = new Role(roleName);
            if (!existingRoles.contains(role))
                rolesToUpdate.add(role);
        }
        return rolesToUpdate;
    }
}
