package pl.com.bottega.documentmanagement.controller;

/**
 * Created by bartosz.paszkowski on 03.07.2016.
 */
public class LoginRequest {

    private String login;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}
