package pl.com.bottega.documentmanagement.infrastructure;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.EmployeeDetails;
import pl.com.bottega.documentmanagement.api.PrintSystemFacade;
import pl.com.bottega.documentmanagement.domain.Document;

import java.util.Set;

/**
 * Created by maciuch on 21.08.16.
 */
@Component
public class PrintSystemFacadeImpl implements PrintSystemFacade {

    @Override
    public void printDocument(Document document, Set<EmployeeDetails> employeeDetailsSet) {
        employeeDetailsSet.forEach(this::logPrinting);
        //employeeDetailsSet.forEach((e) -> logPrinting(e));
    }

    private void logPrinting(EmployeeDetails employeeDetails) {
        String msg = String.format("Printing document for %s", employeeDetails);
        Logger.getLogger(PrintSystemFacade.class).info(msg);
    }

}
