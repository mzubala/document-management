package pl.com.bottega.documentmanagement.controller;

import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

import java.util.Set;

/**
 * Created by Admin on 13.08.2016.
 */
public class PublishRequest {

    private Set<EmployeeId> targetEmployees;

    public Set<EmployeeId> getTargetEmployees() {
        return targetEmployees;
    }

    public void setTargetEmployees(Set<EmployeeId> targetEmployees) {
        this.targetEmployees = targetEmployees;
    }
}
