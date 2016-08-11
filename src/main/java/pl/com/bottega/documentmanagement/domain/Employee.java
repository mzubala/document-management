package pl.com.bottega.documentmanagement.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.Set;

import static com.google.common.base.Preconditions.checkState;



@Entity
public class Employee {
                // nie będzie traktowana jako nowa tabelka tylko mapowane na kolumny w employee
    @EmbeddedId // to id nie będzie traktowana jako osobna tabelka tylko zostanie wstawione do tabelki employee
    private EmployeeId employeeId;
    private String hashedPassword;
    @NaturalId
    private String login;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;


    private Employee(){}

    public Employee(String login, String hashedPassword, EmployeeId employeeId) {
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.employeeId = employeeId;
    }

    public boolean isRegistered(){
        return login != null;
    }

    public void setupAccount(String login, String password){
        checkState(!isRegistered());
        this.login = login;
        this.hashedPassword = password;
    }

    public boolean hasRoles(String ...roleNames){
        for (String role : roleNames) {
            if(!roles.contains(new Role(role)))
                return false;
        }
        return true;
    }

    public void updateRoles(Set<Role> newRole) {
        this.roles = newRole;
    }
}
