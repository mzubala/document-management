package pl.com.bottega.documentmanagement.controller;

import java.util.Set;

/**
 * Created by paulina.pislewicz on 2016-07-30.
 */
public class UpdateRolesRequest {
    private Set<String> roles;

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
