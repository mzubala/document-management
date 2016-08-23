package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.EmployeeDetails;
import pl.com.bottega.documentmanagement.api.MailingFacade;
import pl.com.bottega.documentmanagement.api.PrintSystemFacade;
import pl.com.bottega.documentmanagement.domain.Document;

import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by bartosz.paszkowski on 21.08.2016.
 */
@Component
public class PrintSystemFacadeImpl implements PrintSystemFacade {

    @Override
    public void printDocument(Document document, Set<EmployeeDetails> employeeDetailsSet) {
        employeeDetailsSet.forEach(this::logPrinting);

//        for (EmployeeDetails employeeDet : employeeDetailsSet){
//            logPrinting(employeeDet);
//
//        }
    }

    private void logPrinting( EmployeeDetails employeeDet) {
        String msg = String.format("Printing document for %s", employeeDet);
        Logger.getLogger(String.valueOf(MailingFacade.class)).info(msg);
    }
}
