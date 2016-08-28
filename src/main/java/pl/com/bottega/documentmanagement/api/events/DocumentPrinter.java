package pl.com.bottega.documentmanagement.api.events;

import com.google.common.collect.Sets;
import pl.com.bottega.documentmanagement.api.EmployeeDetails;
import pl.com.bottega.documentmanagement.api.HRSystemFacade;
import pl.com.bottega.documentmanagement.api.PrintSystemFacade;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.Reader;
import pl.com.bottega.documentmanagement.domain.events.DocumentListener;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by anna on 27.08.2016.
 */
public class DocumentPrinter implements DocumentListener {

    private PrintSystemFacade printSystemFacade;
    private HRSystemFacade hrSystemFacade;

    public DocumentPrinter(PrintSystemFacade printSystemFacade, HRSystemFacade hrSystemFacade) {
        this.printSystemFacade = printSystemFacade;
        this.hrSystemFacade = hrSystemFacade;
    }

    @Override
    public void published(Document document) {
        Set<Reader> readers = document.readers();
        //Set<EmployeeId> employeeIds = readers.stream().map(reader -> reader.employeeId(employeeIds));
        Set<EmployeeId> employeeIds = new HashSet<>();
        for (Reader reader : readers)
            employeeIds.add(reader.employeeId());
        Set<EmployeeDetails> employeeDetailsSet = hrSystemFacade.getEmployeeDetails(Sets.newHashSet(employeeIds));
        printDocument(document, employeeDetailsSet);
    }

    private void printDocument(Document document, Set<EmployeeDetails> employeeDetailsSet) {
        Set<EmployeeDetails> employeeWithoutEmail = employeeDetailsSet.stream().filter(employeeDetails -> !employeeDetails.hasEmail()).collect(Collectors.toSet());
        printSystemFacade.printDocument(document, employeeWithoutEmail);
    }
}
