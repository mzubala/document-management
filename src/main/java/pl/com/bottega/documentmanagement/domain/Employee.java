package pl.com.bottega.documentmanagement.domain;

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
    @NaturalId
    private String login;
    private String hashedPassword;
    @ManyToMany (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Embedded
    private Set<Role> roles;

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

   /* public boolean hasRoles(String ... roleName){
        if (roleName.length==0)
            return true;
        else for (String role_name: roleName)
            if (hasRole(role_name))
                    return true;
        return false;

    }
*/

    public void updateRoles(Set<Role> newRoles) {
        this.roles = newRoles;
    }

    public boolean hasRoles(String[] roleNames) {
        if (roleNames.length == 0)
            return true;
        return !Arrays.stream(roleNames).anyMatch((roleName) -> !roles.contains(new Role(roleName)));
    }

}
