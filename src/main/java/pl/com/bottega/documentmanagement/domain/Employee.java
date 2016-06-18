package pl.com.bottega.documentmanagement.domain;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by Wojciech Winiarski on 12.06.16.
 */
public class Employee {

    private String login;
    private String hashedPassword;
    private EmployeeId employeeId;

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
