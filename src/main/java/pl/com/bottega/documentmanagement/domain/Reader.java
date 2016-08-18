package pl.com.bottega.documentmanagement.domain;

import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by paulina.pislewicz on 2016-08-12.
 */

public class Reader<S> {
    Document document;
    @ManyToOne
    Employee employee;
    boolean confirmed;
    Employee confirmedBy;
    Date confirmedAt;


    public Date getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(Date confirmedAt) {
        this.confirmedAt = confirmedAt;
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

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Employee getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(Employee confirmedBy) {
        this.confirmedBy = confirmedBy;
    }


    public Reader(Document document, Employee employee, boolean confirmed, Employee confirmedBy, Date confirmedAt) {
        this.document = document;
        this.employee = employee;
        this.confirmed = confirmed;
        this.confirmedBy = confirmedBy;
        this.confirmedAt = confirmedAt;
    }
    public void confirm() {
        confirmed = true;
        confirmedAt = new Date();
    }

    public void confirm(Employee forEmployee){
        confirmed = true;
        confirmedAt = new Date();
        confirmedBy = forEmployee;

    }

}
