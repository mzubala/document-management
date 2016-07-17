package pl.com.bottega.documentmanagement.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by maciuch on 12.06.16.
 */
@Entity
public class Document {

    @Embedded
    private DocumentNumber documentNumber;
    private String content;
    private String title;
    @Enumerated(EnumType.STRING)
    private  DocumentStatus documentStatus;
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Employee creator;
    @ManyToOne
    private Employee verificator;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt, updatedAt, verificatedAt;


    public Document(DocumentNumber documentNumber, String content, String title, Employee creator) {
        this.documentNumber = documentNumber;
        this.content = content;
        this.title = title;
        this.creator = creator;
        this.documentStatus = DocumentStatus.DRAFT;
        this.createdAt = new Date();
    }

    private Document(){}

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
        this.documentStatus = documentStatus.DRAFT;
        this.updatedAt = new Date();
    }

    public void verify(Employee employee) {
        this.verificator = employee;
        documentStatus = documentStatus.VERIFIED;
        this.verificatedAt = new Date();

    }

    public void confirm(Employee confirmator) {

    }

    public void confirm(Employee confirmator, Employee forEmployee) {

    }
}
