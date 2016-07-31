package pl.com.bottega.documentmanagement.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by Wojciech Winiarski on 12.06.16.
 */
@Entity
public class Employee {

    @EmbeddedId//nie bedzie traktowane jako nowa tabelka tylko mapowane na kolumny w emoloyee
    private EmployeeId employeeId;
    @NaturalId
    private String login;
    private String hashedPassword;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles;

    public Employee(){

    }


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



    public boolean hasRoles(String ...roleNames){
        for (String str : roleNames)
        {

            if (!roles.contains(new Role(str)))
                return false;
        }
        return true;
    }
}
