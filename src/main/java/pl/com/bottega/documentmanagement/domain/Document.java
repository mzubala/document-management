package pl.com.bottega.documentmanagement.domain;

import javax.persistence.*;
import static pl.com.bottega.documentmanagement.domain.DocumentStatus.*;

/**
 * Created by maciuch on 12.06.16.
 */
@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;
    @Embedded
    private DocumentNumber number;
    @ManyToOne
    private Employee creator;
    @Enumerated(EnumType.STRING)
    private DocumentStatus documentStatus;
    @ManyToOne
    private Employee verificator;

    private Document() {}

    public Document(DocumentNumber documentNumber, String content, String title, Employee creator) {
        this.number = documentNumber;
        this.content = content;
        this.title = title;
        this.creator = creator;
        this.documentStatus = DRAFT;
    }

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
        this.documentStatus = DRAFT;
    }

    public void verify(Employee employee) {
        this.verificator = employee;
        this.documentStatus = VERIFIED;
    }

    public void confirm(Employee conirmator) {

    }

    public void confirm(Employee confirmator, Employee forEmployee) {

    }

    public DocumentNumber getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
