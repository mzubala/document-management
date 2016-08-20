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

/**
 * Created by maciuch on 12.06.16.
 */

@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)

public class UserManager {

    private EmployeeRepository employeeRepository;
    private Employee currentEmployee;
    private EmployeeFactory employeeFactory;
    private PasswordHassher passwordHassher;

    private String INITIAL_ROLE = "STAFF";

    @Autowired
    public UserManager (EmployeeRepository employeeRepository, EmployeeFactory employeeFactory, PasswordHassher passwordHassher){
        this.employeeRepository = employeeRepository;
        this.employeeFactory = employeeFactory;
        this.passwordHassher = passwordHassher;
    }
    @Transactional (isolation = Isolation.REPEATABLE_READ)
    public SignupResultDto signup(String login, String password, EmployeeId employeeId){
       Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee == null)
            return setupNewAccount(login, password, employeeId);
        if (employee.isRegistered())
            return failed("Employee already registered");
        if(employeeRepository.isLoginOccupied(login))
            return failed("login occupied");
        employee.setupAccount (login, password);
        employeeRepository.save(employee);
        return success();
        }

    private SignupResultDto setupNewAccount(String login, String password, EmployeeId employeeId) {
        if(employeeRepository.isLoginOccupied(login))
            return failed("Login is occupied!");
        else{
            Employee employee = employeeFactory.create(login,password, employeeId);
            employee.updateRoles(getRoles(INITIAL_ROLE));
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



    @Transactional
    public SignupResultDto login(String login, String password){
       this.currentEmployee = employeeRepository.findByLoginAndPassword(login, passwordHassher.hashedPassword(password));
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

    @Transactional
    @RequiresAuth(roles = "ADMIN")
    public void updateRoles(EmployeeId employeeId, Set<String> roleNames) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        employee.updateRoles(getRoles(roleNames));
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
