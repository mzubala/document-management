package pl.com.bottega.documentmanagement.controller;

import java.util.Set;

/**
 * Created by paulina.pislewicz on 2016-08-20.
 */
public class PublicationRequest {

        private Set<Long> employeeIdsObligatedToRead;

        public Set<Long> getEmployeeIdsObligatedToRead() {
            return employeeIdsObligatedToRead;
        }

        public void setEmployeeIdsObligatedToRead(Set<Long> employeeIdsObligatedToRead) {
            this.employeeIdsObligatedToRead = employeeIdsObligatedToRead;
        }
    }
