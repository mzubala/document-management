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
    @NaturalId
    private String login;
    private String hashedPassword;
    @ManyToMany (fetch = FetchType.EAGER)
    @Embedded
    private Collection<Role> role;

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
        if (rolesNames.length==0)
            return true;
        else for (String role_name: rolesNames)
            if (hasTheRole(role_name))
                    return true;
        return false;

    }

    private boolean hasTheRole(String role_name) {
        return ((role.contains(new Role(role_name))));
    }
}
