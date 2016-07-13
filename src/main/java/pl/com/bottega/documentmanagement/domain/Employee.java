package pl.com.bottega.documentmanagement.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by maciuch on 12.06.16.
 */
@Entity
public class Employee {

    @EmbeddedId
    private EmployeeId employeeId;
    @NaturalId
    private String login;
    private String hashedPassword;
    @ManyToMany
    private Role role;

    public Employee (String login, String hashedPassword, EmployeeId employeeId){
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.employeeId = employeeId;
    }
    private Employee(){}

    public boolean isRegistered() {
        return login != null;
    }

    public void setupAccount(String login, String password) {
        checkState(!isRegistered());
        this.hashedPassword = password;
    }

    public boolean hasRoles(String ... rolesNames){
        for (String role_name: rolesNames)
            if (hasTheRole(role_name)|| (rolesNames.length==0))
                    return true;
        return false;

    }

    private boolean hasTheRole(String role_name) {
        return role.name.equals(role_name);
    }
}
