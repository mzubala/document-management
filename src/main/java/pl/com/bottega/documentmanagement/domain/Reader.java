package pl.com.bottega.documentmanagement.domain;

import com.google.common.base.MoreObjects;
import pl.com.bottega.documentmanagement.infrastructure.DocumentEmployee;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

/**
 * Created by Admin on 12.08.2016.
 */
@Entity
@IdClass(DocumentEmployee.class)
public class Reader {

    @Id
    @ManyToOne(fetch = EAGER)
    private Document document;

    @Id
    @ManyToOne(fetch = EAGER)
    private Employee employee;

    @ManyToOne(fetch = LAZY)
    private Employee confirmedBy;

    private Boolean isConfirmed;
    private Date confirmedAt;

    public Reader() {}

    public Reader(Document document, Employee employee) {
        this.document = document;
        this.employee = employee;
        this.isConfirmed = false;
    }

    public Reader(DocumentEmployee documentEmployee) {
        this(documentEmployee.getDocument(), documentEmployee.getEmployee());
    }



    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public Employee getTargetEmployee() {
        return employee;
    }

    public void setTargetEmployee(Employee targetEmployee) {
        this.employee = targetEmployee;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }

    public Employee getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(Employee confirmedFor) {
        this.confirmedBy = confirmedFor;
    }

    public Date getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(Date confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public void confirm() {
        confirm(this.employee);
    }

    public void confirm(Employee employee) {
        this.confirmedBy = employee;
        this.confirmedAt = new Date();
        this.isConfirmed = true;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("document", document.getNumber())
                .add("employee", employee.getEmployeeId())
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reader reader = (Reader) o;

        if (!document.equals(reader.document)) return false;
        return employee.getEmployeeId().equals(reader.employee.getEmployeeId());
    }

    @Override
    public int hashCode() {
        int result = document.getNumber().hashCode();
        result = 31 * result + employee.getEmployeeId().hashCode();
        return result;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }
}
