package pl.com.bottega.documentmanagement.api.events;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.EmployeeDetails;
import pl.com.bottega.documentmanagement.api.HRSystemFasade;
import pl.com.bottega.documentmanagement.api.PrintSystemFasade;
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
public class DocumentPrinter implements DocumentListener {

    private PrintSystemFasade printSystemFasade;
    private HRSystemFasade hrSystemFasade;

    public DocumentPrinter(PrintSystemFasade printSystemFasade, HRSystemFasade hrSystemFasade) {
        this.printSystemFasade = printSystemFasade;
        this.hrSystemFasade = hrSystemFasade;
    }

    @Override
    public void published(Document document) {
        Set<Reader> readers = document.getReaders();
        Set<EmployeeId> employeeIds = readers.stream().map(reader -> reader.getEmployeeId()).collect(Collectors.toSet());
        Set<EmployeeDetails> employeeDetailsSet = hrSystemFasade.getEmployeeDetails(employeeIds);
        printDocument(document, employeeDetailsSet);
    }

    private void printDocument(Document document, Set<EmployeeDetails> employeeDetailsSet) {
        Set<EmployeeDetails> employeesWithoutMail = employeeDetailsSet.stream().filter((employeeDetails -> !employeeDetails.hasEmail())).collect(Collectors.toSet());
        printSystemFasade.printDocument(document, employeesWithoutMail);
    }
}
