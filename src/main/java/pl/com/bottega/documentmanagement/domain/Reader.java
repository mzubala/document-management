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
}
