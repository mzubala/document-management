package pl.com.bottega.documentmanagement.api.events;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.EmployeeDetails;
import pl.com.bottega.documentmanagement.api.HRSystemFasade;
import pl.com.bottega.documentmanagement.api.MailingFasade;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.Reader;
import pl.com.bottega.documentmanagement.domain.events.DocumentListener;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Dell on 2016-08-27.
 */
//@Component
public class DocumentPublishedNotifier implements DocumentListener{


    private MailingFasade mailingFasade;
    private HRSystemFasade hrSystemFasade;

    public DocumentPublishedNotifier(MailingFasade mailingFasade, HRSystemFasade hrSystemFasade) {
        this.mailingFasade = mailingFasade;
        this.hrSystemFasade = hrSystemFasade;
    }

    @Override
    public void published(Document document) {
        Set<Reader> readers = document.getReaders();
        Set<EmployeeId> employeeIds = readers.stream().map(reader -> reader.getEmployeeId()).collect(Collectors.toSet());
        Set<EmployeeDetails> employeeDetailsSet = hrSystemFasade.getEmployeeDetails(employeeIds);
        sendEmailsAboutPublishedDocument(document, employeeDetailsSet);
    }

    private void sendEmailsAboutPublishedDocument(Document document, Set<EmployeeDetails> employeeDetailsSet) {
        Set<EmployeeDetails> employeesWithMail = employeeDetailsSet.stream().filter(EmployeeDetails::hasEmail).collect(Collectors.toSet());
        mailingFasade.sendDocumentPublishedEmails(document, employeesWithMail);
    }
}
