package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.EmployeeDetails;
import pl.com.bottega.documentmanagement.api.HRSystemFacade;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by anna on 21.08.2016.
 */
@Component
public class HRSystemFacadeImpl implements HRSystemFacade {

    @Override
    public Set<EmployeeDetails> getEmployeeDetails(Set<EmployeeId> employees) {
        /*
        Set<EmployeeDetails> employeeDetailsSet = new HashSet<>();
        for(Employee employee : employees) {
            EmployeeDetails employeeDetails = new EmployeeDetails();
            employeeDetails.setFirstName("Jan");
            employeeDetails.setLastName("Nowak");
            employeeDetails.setAddress("Północna 13");
            employeeDetails.setEmail("jan.nowak@gmail.com");
            employeeDetailsSet.add(employeeDetails);
        }
        return employeeDetailsSet;
        */
        //funkcyjnie
        return employees.stream().map((employee) -> {
            EmployeeDetails employeeDetails = new EmployeeDetails();
            employeeDetails.setFirstName("Jan");
            employeeDetails.setLastName("Nowak");
            employeeDetails.setAddress("Północna 13");
            employeeDetails.setEmail("jan.nowak@gmail.com");
            return employeeDetails;
        }).collect(Collectors.toSet());
    }
}
