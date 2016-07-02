package pl.com.bottega.documentmanagement.domain;

import javax.persistence.*;

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

    public Document(DocumentNumber documentNumber, String content, String title) {
        this.documentNumber = documentNumber;
        this.content = content;
        this.title = title;
    }

    public Document(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void change(String title, String content) {
        setTitle(title);
        setContent(content);
    }

    public void verify(Employee employee) {

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
}
