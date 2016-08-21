package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.EmployeeId;

import java.util.Set;

/**
 * Created by maciuch on 21.08.16.
 */
public interface HRSystemFacade {

    Set<EmployeeDetails> getEmployeeDetails(Set<EmployeeId> employee);

}
