package pl.com.bottega.documentmanagement.infrastructure;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.EmployeeDetails;
import pl.com.bottega.documentmanagement.api.MailingFasade;
import pl.com.bottega.documentmanagement.domain.Document;

import java.util.Set;

/**
 * Created by Dell on 2016-08-21.
 */
@Component
public class MailingFasadeImpl implements MailingFasade {

    @Override
    public void sendDocumentPublishedEmails(Document document, Set<EmployeeDetails> employeeDetailsSet) {
//        for (EmployeeDetails employeeDetails : employeeDetailsSet)
//            Logger.getLogger(MailingFasade.class).info("Document has been send to " + employeeDetails.getFirstName() + " " + employeeDetails.getLastName() + "(" + employeeDetails.getEmail() + ")");
        employeeDetailsSet.stream().forEach((e) -> logMailing(document, e));
    }

    private void logMailing(Document document, EmployeeDetails employeeDetails) {
        String msg = String.format("Mailing info to %s about published document %s", employeeDetails.getEmail(), employeeDetails);
        Logger.getLogger(MailingFasade.class).info(msg);
    }
}
