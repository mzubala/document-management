package pl.com.bottega.documentmanagement.domain;

import javax.persistence.*;
import java.util.Date;

import static pl.com.bottega.documentmanagement.domain.DocumentStatus.DRAFT;
import static pl.com.bottega.documentmanagement.domain.DocumentStatus.VERIFIED;

/**
 * Created by Wojciech Winiarski on 12.06.16.
 */
@Entity
public class Document {


    @Id
    @GeneratedValue
    private Long documentId;
    @Embedded
    private DocumentNumber documentNumber;
    private String content;
    private String title;

    @Enumerated(EnumType.STRING)
    private DocumentStatus documentStatus;

    @ManyToOne
    private Employee creator;

    @ManyToOne
    private Employee veryficator;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date verificatedAt;



    private Document(){}

    public Document(DocumentNumber documentNumber, String content, String title, Employee creator) {

        this.documentNumber = documentNumber;
        this.content = content;
        this.title = title;
        this.creator = creator;
        this.documentStatus = DRAFT;
        this.createAt = new Date();


    }

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
        this.documentStatus = DRAFT;
        this.updatedAt = new Date();


    }

    public void verify(Employee employee) {

       this.veryficator = employee;
       this.documentStatus = VERIFIED;
       this.verificatedAt = new Date();



    }

    public void confirm(Employee conirmator) {

    }

    public void confirm(Employee confirmator, Employee forEmployee) {

    }

}
