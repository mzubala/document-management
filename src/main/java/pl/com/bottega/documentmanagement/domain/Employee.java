package pl.com.bottega.documentmanagement.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
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
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<Role> roles;


    public Employee(){}

    public Employee(EmployeeId employeeId){
        this.employeeId = employeeId;
    }

    public Employee(String login, String hashedPassword, EmployeeId employeeId) {
        this.login = login;
        this.hashedPassword = hashedPassword;
        this.employeeId = employeeId;
    }
    public EmployeeId employeeId() {
        return employeeId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId.equals(employee.employeeId);

    }

    @Override
    public int hashCode() {
        return employeeId.hashCode();
    }
}
