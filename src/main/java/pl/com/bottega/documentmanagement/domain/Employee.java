package pl.com.bottega.documentmanagement.domain;

import com.google.common.base.MoreObjects;
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

    protected Employee() {}

    public Employee(String login, String hashedPassword, EmployeeId employeeId) {
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.employeeId = employeeId;
    }

    public Employee(EmployeeId id) {
        this.employeeId = id;
        this.login = "unregisteredEmployee" + id;
        this.hashedPassword = null;
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

    public EmployeeId getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(EmployeeId employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("employeeId", employeeId)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        return employeeId.equals(employee.employeeId);
    }

    @Override
    public int hashCode() {
        return employeeId.hashCode();
    }
}
