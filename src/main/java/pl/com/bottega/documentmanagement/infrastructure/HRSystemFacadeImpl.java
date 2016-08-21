package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.EmployeeDetails;
import pl.com.bottega.documentmanagement.api.HRSystemFasade;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Dell on 2016-08-21.
 */
@Component
public class HRSystemFacadeImpl implements HRSystemFasade {

    @Override
    public Set<EmployeeDetails> getEmployeeDetails(Set<EmployeeId> employees) {
//        Set<EmployeeDetails> employeeDetailsSet = new HashSet<>();
//        for (Employee employee : employees) {
//            EmployeeDetails employeeDetails = new EmployeeDetails();
//            employeeDetails.setFirstName("Jan");
//            employeeDetails.setLastName("Kowalski");
//            employeeDetails.setEmail("Kowalski@f.com");
//            employeeDetails.setAddress("ul. Kowalska 57");
//            employeeDetailsSet.add(employeeDetails);
//        }
//        return employeeDetailsSet;

        return employees.stream().map((employee) -> {
            EmployeeDetails employeeDetails = new EmployeeDetails();
            employeeDetails.setFirstName("Jan");
            employeeDetails.setLastName("Kowalski");
            employeeDetails.setEmail("Kowalski@f.com");
            employeeDetails.setAddress("ul. Kowalska 57");
            return employeeDetails;
        }).collect(Collectors.toSet());
    }
}
