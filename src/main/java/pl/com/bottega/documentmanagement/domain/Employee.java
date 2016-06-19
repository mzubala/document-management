package pl.com.bottega.documentmanagement.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by maciuch on 12.06.16.
 */
@Entity
public class Employee {

    @EmbeddedId
    private EmployeeId employeeId;
    private String login;
    private String hashedPassword;

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
}
