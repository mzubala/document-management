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
    @Id
    @GeneratedValue
    Long id;
    @ManyToOne
    private Employee creator;

    public Document(DocumentNumber documentNumber, String content, String title, Employee creator) {
        this.documentNumber = documentNumber;
        this.content = content;
        this.title = title;
        this.creator = creator;
    }
    private Document(){}

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
