package pl.com.bottega.documentmanagement.api.events;

import com.google.common.collect.Sets;
import pl.com.bottega.documentmanagement.api.EmployeeDetails;
import pl.com.bottega.documentmanagement.api.HRSystemFacade;
import pl.com.bottega.documentmanagement.api.MailingFacade;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.Reader;
import pl.com.bottega.documentmanagement.domain.events.DocumentListener;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by maciuch on 27.08.16.
 */
public class DocumentPublishedNotifier implements DocumentListener {

    private MailingFacade mailingFacade;
    private HRSystemFacade hrSystemFacade;

    public DocumentPublishedNotifier(MailingFacade mailingFacade, HRSystemFacade hrSystemFacade) {
        this.mailingFacade = mailingFacade;
        this.hrSystemFacade = hrSystemFacade;
    }

    @Override
    public void published(Document document) {
        Set<Reader> readers = document.readers();
        //Set<EmployeeId> employeeIds = readers.stream().map(reader -> reader.employeeId()).collect(Collectors.toSet());
        Set<EmployeeId> employeeIds = new HashSet<>();
        for(Reader reader : readers)
            employeeIds.add(reader.employeeId());
        Set<EmployeeDetails> employeeDetailsSet = hrSystemFacade.getEmployeeDetails(Sets.newHashSet(employeeIds));
        sendEmailsAboutPublishedDocument(document, employeeDetailsSet);
    }

    private void sendEmailsAboutPublishedDocument(Document document, Set<EmployeeDetails> employeeDetailsSet) {
        Set<EmployeeDetails> employeesWithEmail = employeeDetailsSet.stream().
                filter(EmployeeDetails::hasEmail).collect(Collectors.toSet());
        mailingFacade.sendDocumentPublishedEmails(document, employeesWithEmail);
    }

}
