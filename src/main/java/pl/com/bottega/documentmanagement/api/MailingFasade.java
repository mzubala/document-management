package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.Document;

import java.util.Set;

/**
 * Created by Dell on 2016-08-21.
 */
public interface MailingFasade {

    void sendDocumentPublishedEmails(Document document, Set<EmployeeDetails> employeeDetailsSet);
}
