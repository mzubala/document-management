package pl.com.bottega.documentmanagement.acceptance.mock;

import pl.com.bottega.documentmanagement.api.EmployeeDetails;
import pl.com.bottega.documentmanagement.api.PrintSystemFacade;
import pl.com.bottega.documentmanagement.domain.Document;

import java.util.Set;

/**
 * Created by maciuch on 28.08.16.
 */
public class MockPrintingSystemFacade implements PrintSystemFacade {
    @Override
    public void printDocument(Document document, Set<EmployeeDetails> employeeDetailsSet) {

    }
}
