package pl.com.bottega.documentmanagement.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by paulina.pislewicz on 2016-08-12.
 */

public class Reader<S> {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    Document document;
    @ManyToOne (cascade = CascadeType.ALL)
    Employee employee;

    boolean confirmed;

    @ManyToOne
    Employee confirmedBy;
    @Temporal(TemporalType.TIMESTAMP)
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


    public Reader(Document document, Employee employee) {
        this.document = document;
        this.employee = employee;
        this.confirmed = false;
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
