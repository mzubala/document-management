package pl.com.bottega.documentmanagement.domain;

import com.google.common.base.Objects;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by maciuch on 12.06.16.
 */
@Entity
public class Employee {

    @EmbeddedId
    private EmployeeId employeeId;
    private String hashedPassword;
    @NaturalId
    private String login;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Role> roles;

    protected Employee() {
    }

    public Employee(String login, String hashedPassword, EmployeeId employeeId) {
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.employeeId = employeeId;
    }

    public Employee(EmployeeId employeeId) {
        this.employeeId = employeeId;
    }

    public boolean isRegistered() {
        return login != null;
    }

    public void setupAccount(String login, String password) {
        checkState(!isRegistered());
        this.login = login;
        this.hashedPassword = password;
    }

    public void updateRoles(Set<Role> newRoles) {
        this.roles = newRoles;
    }

    public boolean hasRoles(String[] roleNames) {
        if (roleNames.length == 0)
            return true;
        return !Arrays.stream(roleNames).anyMatch((roleName) -> !roles.contains(new Role(roleName)));
    }

    public EmployeeId employeeId() {
        return employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equal(employeeId, employee.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(employeeId);
    }

}
