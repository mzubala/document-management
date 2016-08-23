package pl.com.bottega.documentmanagement.controller;

import java.util.Set;

/**
 * Created by bartosz.paszkowski on 18.08.2016.
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
