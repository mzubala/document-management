package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.EmployeeId;

import java.util.Set;

/**
 * Created by anna on 21.08.2016.
 */
public interface HRSystemFacade {

    Set<EmployeeDetails> getEmployeeDetails(Set<EmployeeId> employee);
}
