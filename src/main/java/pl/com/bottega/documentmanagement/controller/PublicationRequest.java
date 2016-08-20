package pl.com.bottega.documentmanagement.controller;

import java.util.Set;

/**
 * Created by maciuch on 16.08.16.
 */
public class PublicationRequest {

    private Set<Long> employeeIds;

    public Set<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(Set<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }
}
