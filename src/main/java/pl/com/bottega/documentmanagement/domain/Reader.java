package pl.com.bottega.documentmanagement.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Dell on 2016-08-17.
 */
@Entity
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Document document;

    @ManyToOne
    private Employee employee;

    private boolean confirmed;

    @ManyToOne
    private Employee confirmedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmedAt;

    public Reader() {
    }

    public Reader(Document document, Employee employee) {
        this.document = document;
        this.employee = employee;
        this.confirmed = false;
    }

    public void confirm() {
        this.confirmed = true;
        this.confirmedAt = new Date();
    }

    public void confirm(Employee employee) {
        confirm();
        this.confirmedBy = employee;
    }

    public Document getDocument() {
        return document;
    }

    public Employee getEmployee() {
        return employee;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Employee getConfirmedBy() {
        return confirmedBy;
    }

    public Date getConfirmedAt() {
        return confirmedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reader reader = (Reader) o;

        if (confirmed != reader.confirmed) return false;
        if (!document.equals(reader.document)) return false;
        if (!employee.equals(reader.employee)) return false;
        if (confirmedBy != null ? !confirmedBy.equals(reader.confirmedBy) : reader.confirmedBy != null) return false;
        return confirmedAt != null ? confirmedAt.equals(reader.confirmedAt) : reader.confirmedAt == null;

    }

    @Override
    public int hashCode() {
        int result = document.hashCode();
        result = 31 * result + employee.hashCode();
        result = 31 * result + (confirmed ? 1 : 0);
        result = 31 * result + (confirmedBy != null ? confirmedBy.hashCode() : 0);
        result = 31 * result + (confirmedAt != null ? confirmedAt.hashCode() : 0);
        return result;
    }
}
