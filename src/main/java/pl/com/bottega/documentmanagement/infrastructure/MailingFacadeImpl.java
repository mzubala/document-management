package pl.com.bottega.documentmanagement.infrastructure;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.EmployeeDetails;
import pl.com.bottega.documentmanagement.api.MailingFacade;
import pl.com.bottega.documentmanagement.domain.Document;

import java.util.Set;

/**
 * Created by maciuch on 21.08.16.
 */
@Component
public class MailingFacadeImpl implements MailingFacade {

    @Override
    public void sendDocumentPublishedEmails(Document document, Set<EmployeeDetails> employeeDetailsSet) {
        employeeDetailsSet.stream().forEach((e) -> logMailing(document, e));
    }

    private void logMailing(Document document, EmployeeDetails employeeDetails) {
        String msg = String.format("Mailing info to %s about published document %s", employeeDetails.getEmail(), document.title());
        Logger.getLogger(MailingFacadeImpl.class).info(msg);
    }

}
