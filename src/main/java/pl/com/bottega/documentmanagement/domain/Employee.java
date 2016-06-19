package pl.com.bottega.documentmanagement.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by Wojciech Winiarski on 12.06.16.
 */
@Entity
public class Employee {

    @EmbeddedId//nie bedzie traktowane jako nowa tabelka tylko mapowane na kolumny w emoloyee
    private EmployeeId employeeId;
    private String login;
    private String hashedPassword;

    private Employee(){

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
}
