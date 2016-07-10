package pl.com.bottega.documentmanagement.domain;

import javax.persistence.*;

/**
 * Created by maciuch on 12.06.16.
 */
@Entity
public class Document {

    @Embedded
    DocumentNumber documentNumber;
    String content;
    String title;
    @Enumerated(EnumType.STRING)
    DocumentStatus documentStatus;
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    private Employee creator;
    @ManyToOne
    private Employee verificator;


    public Document(DocumentNumber documentNumber, String content, String title, Employee creator) {
        this.documentNumber = documentNumber;
        this.content = content;
        this.title = title;
        this.creator = creator;
        this.documentStatus = DocumentStatus.DRAFT;
    }

    private Document(){}

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
        this.documentStatus = documentStatus.DRAFT;
    }

    public void verify(Employee employee) {
        this.verificator = employee;
        documentStatus = documentStatus.VERIFIED;

    }

    public void confirm(Employee confirmator) {

    }

    public void confirm(Employee confirmator, Employee forEmployee) {

    }

}
