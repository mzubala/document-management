package pl.com.bottega.documentmanagement.domain;

import javax.persistence.*;
import java.util.Date;


/**
 * Created by maciuch on 12.06.16.
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
    private Employee verifier;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
    @Temporal(TemporalType.TIMESTAMP)
    private Date verificationAt;

    private Document(){}

    public Document(DocumentNumber documentNumber, String content, String title, Employee creator) {
        this.documentNumber = documentNumber;
        this.content = content;
        this.title = title;
        this.creator = creator;
        this.documentStatus = DocumentStatus.DRAFT;
        this.createdAt = new Date();

    }

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
        this.documentStatus = DocumentStatus.DRAFT;
        this.updatedAt = new Date();

    }

    public void verify(Employee employee) {
        this.verifier = employee;
        this.documentStatus = DocumentStatus.VERIFIED;
        this.verificationAt = new Date();

    }

    public void confirm(Employee conirmator) {

    }

    public void confirm(Employee confirmator, Employee forEmployee) {

    }

}
