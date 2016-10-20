package pl.com.bottega.documentmanagement.acceptance.mock;

import pl.com.bottega.documentmanagement.api.EmployeeDetails;
import pl.com.bottega.documentmanagement.api.HRSystemFasade;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

import java.util.Set;

/**
 * Created by Dell on 2016-08-28.
 */
public class MockHRSystemFasade implements HRSystemFasade {

    @Override
    public Set<EmployeeDetails> getEmployeeDetails(Set<EmployeeId> employeeIds) {
        return null;
    }
}
