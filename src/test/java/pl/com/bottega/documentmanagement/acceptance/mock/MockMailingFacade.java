package pl.com.bottega.documentmanagement.acceptance.mock;

import pl.com.bottega.documentmanagement.api.EmployeeDetails;
import pl.com.bottega.documentmanagement.api.MailingFacade;
import pl.com.bottega.documentmanagement.domain.Document;

import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Created by maciuch on 28.08.16.
 */
public class MockMailingFacade implements MailingFacade {
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
