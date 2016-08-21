package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.Document;

import java.util.Set;

/**
 * Created by Dell on 2016-08-21.
 */
public interface PrintSystemFasade {

    void printDocument(Document document, Set<EmployeeDetails> employeeDetailsSet);

}
