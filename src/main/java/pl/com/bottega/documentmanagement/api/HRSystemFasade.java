package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.EmployeeId;

import java.util.Set;

/**
 * Created by Dell on 2016-08-21.
 */
public interface HRSystemFasade {

    Set<EmployeeDetails> getEmployeeDetails(Set<EmployeeId> employeeIds);
}
