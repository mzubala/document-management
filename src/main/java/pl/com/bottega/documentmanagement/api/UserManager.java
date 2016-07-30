package pl.com.bottega.documentmanagement.api;

import com.google.common.base.Charsets;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import com.google.common.hash.Hashing;
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
import java.util.stream.Collectors;

/**
 * Created by maciuch on 12.06.16.
 */
@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserManager {

    private String INITIAL_ROLE = "STAFF";

    private EmployeeRepository employeeRepository;
    private Employee currentEmployee;

    public UserManager(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
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
            Employee employee = new Employee(login, hashedPassword(password), employeeId);
            employee.updateRoles(getRoles(INITIAL_ROLE));
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
        if (this.currentEmployee == null)
            return failed("login or password incorrect");
        else
            return success();
    }

    public Employee currentEmployee() {
        return this.currentEmployee;
    }

    public boolean isAuthenticated(String... roles) {
        return currentEmployee != null && currentEmployee.hasRoles(roles);
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
                existingRoles.add(role);
        }
        return rolesToUpdate;
    }
}
