package pl.com.bottega.documentmanagement.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

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
    @ManyToOne
    private Employee publisher;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt, updatedAt, verificatedAt, publishedAt;
    private Boolean deleted;
    @ManyToMany (cascade = CascadeType.ALL)//, fetch = FetchType.EAGER) - by pobierać chciwie tagi i zniwelować n+1 problem
    private Set<Tag> tags;


    public Document(DocumentNumber documentNumber, String content, String title, Employee creator) {
        this.documentNumber = documentNumber;
        this.content = content;
        this.title = title;
        this.creator = creator;
        this.documentStatus = DocumentStatus.DRAFT;
        this.createdAt = new Date();
        this.deleted = false;
    }

    private Document(){}

    public void create(String title, String content){
        this.title = title;
        this.content = content;
        this.createdAt = new Date();
    }

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
        this.documentStatus = documentStatus.DRAFT;
        this.updatedAt = new Date();
    }

    public void verify(Employee employee) {
        checkArgument(employee!=null);
        this.verificator = employee;
        documentStatus = documentStatus.VERIFIED;
        this.verificatedAt = new Date();
    }

    public void confirm(Employee confirmator) {

    }

    public void confirm(Employee confirmator, Employee forEmployee) {

    }

    public void delete() {
        this.deleted = true;
    }

    public void tag (Set<Tag> tags){
        this.tags = tags;
    }

    public Set <Tag> tags (){
        return tags;
    }

    public String content(){
        return content;
    }
    public Employee creator(){
        return creator;
    }
    public String title(){
        return title;
    }
    public boolean deleted(){
        return deleted;
    }
    public DocumentNumber number(){
        return documentNumber;
    }
    public DocumentStatus documentStatus(){
        return documentStatus;
    }

    public Date verificatedAt(){
        return verificatedAt;
    }

    public Employee verificator() {
        return verificator;
    }
}
