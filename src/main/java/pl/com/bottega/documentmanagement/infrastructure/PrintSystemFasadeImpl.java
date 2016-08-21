package pl.com.bottega.documentmanagement.infrastructure;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.EmployeeDetails;
import pl.com.bottega.documentmanagement.api.PrintSystemFasade;
import pl.com.bottega.documentmanagement.domain.Document;

import java.util.Set;

/**
 * Created by Dell on 2016-08-21.
 */
@Component
public class PrintSystemFasadeImpl implements PrintSystemFasade {

    @Override
    public void printDocument(Document document, Set<EmployeeDetails> employeeDetailsSet) {
//        for (EmployeeDetails employeeDetails : employeeDetailsSet)
//            Logger.getLogger(PrintSystemFasade.class).info("Document has been been printed for " + employeeDetails.getFirstName() + " " + employeeDetails.getLastName());
//        employeeDetailsSet.stream().forEach(this :: logPrinting);
        employeeDetailsSet.forEach(this :: logPrinting);
    }

    private void logPrinting(EmployeeDetails employeeDetails) {
        String msg = String.format("Printing document for %s", employeeDetails);
        Logger.getLogger(PrintSystemFasade.class).info(msg);
    }
}
