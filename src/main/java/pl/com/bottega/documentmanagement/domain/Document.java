package pl.com.bottega.documentmanagement.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

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

    public Document(DocumentNumber documentNumber, String content, String title) {
        this.documentNumber = documentNumber;
        this.content = content;
        this.title = title;
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
