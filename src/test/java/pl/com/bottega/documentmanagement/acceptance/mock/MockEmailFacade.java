package pl.com.bottega.documentmanagement.acceptance.mock;

import pl.com.bottega.documentmanagement.api.EmployeeDetails;
import pl.com.bottega.documentmanagement.api.MailingFasade;
import pl.com.bottega.documentmanagement.domain.Document;

import java.util.Set;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Dell on 2016-08-28.
 */
public class MockEmailFacade implements MailingFasade {

    private Document document;
    private Set<EmployeeDetails> employeeDetailsSet;

    @Override
    public void sendDocumentPublishedEmails(Document document, Set<EmployeeDetails> employeeDetailsSet) {
        this.document = document;
        this.employeeDetailsSet = employeeDetailsSet;
    }

    public void verify(Document document, Set<EmployeeDetails> employeeDetailsSet) {
        assertEquals(document, this.document);
        assertEquals(employeeDetailsSet, this.employeeDetailsSet);

    }
}
