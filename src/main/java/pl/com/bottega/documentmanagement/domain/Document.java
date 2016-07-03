package pl.com.bottega.documentmanagement.domain;

import pl.com.bottega.documentmanagement.api.DocumentDto;

import javax.persistence.*;
import javax.print.Doc;

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

    @ManyToOne
    private Employee creator;

    private Document(){}

    public Document(DocumentNumber documentNumber, String content, String title, Employee creator) {

        this.documentNumber = documentNumber;
        this.content = content;
        this.title = title;
        this.creator = creator;

    }

    public void change(String title, String content) {
        this.title = title;
        this.content = content;

    }

    public void verify(Employee employee) {

    }

    public void confirm(Employee conirmator) {

    }

    public void confirm(Employee confirmator, Employee forEmployee) {

    }

}
