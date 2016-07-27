package pl.com.bottega.documentmanagement.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

/**
 * Created by maciuch on 12.06.16.
 */
@Entity
public class Employee {

    @EmbeddedId
    private EmployeeId employeeId;
    private String hashedPassword;

    @ManyToMany(cascade = ALL, fetch = EAGER)
    private List<Role> roles;

    @NaturalId
    private String login;

    private Employee(){}

    public Employee(String login, String hashedPassword, EmployeeId employeeId) {
        this.login = login;
        this.hashedPassword = hashedPassword;
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

    public boolean hasRoles(String... roleNames) {
        if(roleNames == null || roleNames.length == 0) {
            return true;
        }
        for (String role : roleNames) {
            if (roles.contains(new Role(role)))
                return true;
        }
        return false;
    }

    public void setRoles(String... rolesNames) {
        List<Role> result = new ArrayList<>();
        for(String role : rolesNames)
            result.add(new Role(role));
        this.roles = result;
    }
}
