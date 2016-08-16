package pl.com.bottega.documentmanagement.controller;

import java.util.Set;

/**
 * Created by Dell on 2016-07-19.
 */
public class updateRolesRequest {

    private Set<String> roles;

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
