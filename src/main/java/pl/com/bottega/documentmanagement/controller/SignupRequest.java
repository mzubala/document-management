package pl.com.bottega.documentmanagement.controller;

/**
 * Created by Wojciech Winiarski on 03.07.2016.
 */
public class SignupRequest {

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

    private String login;
    private String password;
    private Long employeeId;


}
