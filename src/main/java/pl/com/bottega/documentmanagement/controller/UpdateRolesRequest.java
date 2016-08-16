package pl.com.bottega.documentmanagement.controller;

import java.util.Set;

/**
 * Created by maciuch on 27.07.16.
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
