package pl.com.bottega.documentmanagement.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

import java.util.Collection;

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
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> role;

    private Employee() {}

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

    public boolean hasRoles(String ...roleNames) {
        for (String er : roleNames) {
            if (!role.contains(new Role(er)))
                return false;
        }
        return true;
    }

}
