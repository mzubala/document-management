package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.Document;

import java.util.Set;

/**
 * Created by bartosz.paszkowski on 21.08.2016.
 */
public interface MailingFacade {

    void sendDocumentPublishedEmails(Document document, Set<EmployeeDetails> employeeDetailsSet);

}
