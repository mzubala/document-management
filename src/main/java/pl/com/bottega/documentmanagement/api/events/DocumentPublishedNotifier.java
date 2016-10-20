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

/**
 * Created by bartosz.paszkowski on 27.08.2016.
 */
//@Component
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
        //Set<EmployeeId> employeeIds = readers.stream().map(reader -> reader.employeeId());
        Set<EmployeeId> employeeIds = new HashSet<>();
        for (Reader reader : readers){
            employeeIds.add(reader.employeeId());
        }
        Set<EmployeeDetails> employeeDetailsSet = hrSystemFacade.getEmployeeDetails(Sets.newHashSet(employeeIds));
        sendEmailAboutPublishDocument(document,employeeDetailsSet);
    }

    private void sendEmailAboutPublishDocument(Document document, Set<EmployeeDetails> employeeDetailsSet){
        Set<EmployeeDetails>employeeWithMail = new HashSet<>();
        for (EmployeeDetails employeeDetail : employeeDetailsSet){
            if (employeeDetail.getEmail() != null)
                employeeWithMail.add(employeeDetail);
        }
        mailingFacade.sendDocumentPublishedEmails(document, employeeWithMail);
    }
}
