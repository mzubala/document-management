package pl.com.bottega.documentmanagement.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Set;

/**
 * Created by Seta on 2016-08-18.
 */

public class PublicationRequest {

    private Set<Long> employeeIds;

    public Set<Long> getEmployeeIds(){
        return employeeIds;
    }
    public void setEmployeeIds(Set<Long> employeeIds){
        this.employeeIds = employeeIds;
    }
}
