package pl.com.bottega.documentmanagement.controller;

import java.util.Set;

/**
 * Created by Dell on 2016-08-17.
 */
public class PublicationsRequest {

    private Set<Long> employeeIds;

    public Set<Long> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(Set<Long> employeeIds) {
        this.employeeIds = employeeIds;
    }
}
