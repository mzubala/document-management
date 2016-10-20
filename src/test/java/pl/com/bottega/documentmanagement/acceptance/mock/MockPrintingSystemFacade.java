package pl.com.bottega.documentmanagement.acceptance.mock;

import pl.com.bottega.documentmanagement.api.EmployeeDetails;
import pl.com.bottega.documentmanagement.api.PrintSystemFasade;
import pl.com.bottega.documentmanagement.domain.Document;

import java.util.Set;

/**
 * Created by Dell on 2016-08-28.
 */
public class MockPrintingSystemFacade implements PrintSystemFasade {

    @Override
    public void printDocument(Document document, Set<EmployeeDetails> employeeDetailsSet) {

    }
}
