package pl.com.bottega.documentmanagement.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by maciuch on 12.06.16.
 */
@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private DocumentNumber documentNumber;
    private String title;
    private String content;

    @ManyToOne
    private Employee creator;

    @ManyToOne
    private Employee verificator;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt, verifiedAt, updatedAt;

    @Enumerated(EnumType.STRING)
    private DocumentStatus documentStatus;

    private boolean deleted;

    public Document(DocumentNumber documentNumber, String content, String title, Employee creator) {
        this.documentNumber = documentNumber;
        this.content = content;
        this.title = title;
        this.creator = creator;
        this.documentStatus = DocumentStatus.DRAFT;
        this.createdAt = new Date();
        this.deleted = false;
    }

    private Document() {}

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
        this.documentStatus = DocumentStatus.DRAFT;
        this.updatedAt = new Date();
    }

    public void verify(Employee verificator) {
        this.verificator = verificator;
        this.documentStatus = DocumentStatus.VERIFIED;
        this.verifiedAt = new Date();
    }

    public void confirm(Employee conirmator) {
    }

    public void confirm(Employee confirmator, Employee forEmployee) {
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", documentNumber=" + documentNumber +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public void delete() {
        this.deleted = true;
    }
}
