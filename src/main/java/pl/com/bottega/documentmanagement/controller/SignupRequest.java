package pl.com.bottega.documentmanagement.controller;

/**
 * Created by paulina.pislewicz on 2016-07-03.
 */
public class SignupRequest {
    private String login;
    private String password;
    private Long employeeId;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
