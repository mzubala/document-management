package pl.com.bottega.documentmanagement.domain;

import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.Employee;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Seta on 2016-08-18.
 */
@Entity
public class Reader {

    @ManyToOne
    private Document document;

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Employee employee;

    private boolean confirmed;

    @ManyToOne
    private Employee confirmedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmedAt;

    public Reader(){

    }

    public Reader(Document document, Employee employee){

        this.document = document;
        this.employee = employee;
        this.confirmed = false;

    }

}
