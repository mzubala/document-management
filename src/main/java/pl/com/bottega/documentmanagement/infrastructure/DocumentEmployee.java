package pl.com.bottega.documentmanagement.infrastructure;

import org.hibernate.annotations.NaturalId;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.Employee;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by Admin on 14.08.2016.
 */
public class DocumentEmployee implements Serializable {

    private Document document;
    private Employee employee;

    public DocumentEmployee(Document document, Employee employee) {
        this.document = document;
        this.employee = employee;
    }

    public DocumentEmployee() {
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
