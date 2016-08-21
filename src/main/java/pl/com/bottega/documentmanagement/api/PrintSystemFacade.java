package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.Document;

import java.util.Set;

/**
 * Created by maciuch on 21.08.16.
 */
public interface PrintSystemFacade {

    void printDocument(Document document, Set<EmployeeDetails> employeeDetailsSet);

}
