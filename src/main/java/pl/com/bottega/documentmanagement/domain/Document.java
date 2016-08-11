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

    /**
     * true if document is deleted
     */
    private Boolean deleted;
    @ManyToOne
    private Employee deletedBy;

    @ManyToMany(cascade = CascadeType.ALL) //zapis dokumentu pociagnie zapis tagów
    private Set<Tag> tags;

    private Document(){}

    public Document(DocumentNumber documentNumber, String content, String title, Employee creator) {
        this.documentNumber = documentNumber;
        this.content = content;
        this.title = title;
        this.creator = creator;
        this.documentStatus = DocumentStatus.DRAFT;
        this.createdAt = new Date();
        this.deleted = false;

    }

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
        this.documentStatus = DocumentStatus.DRAFT;
        this.updatedAt = new Date();

    }

    public void verify(Employee employee) {
        checkArgument(employee != null);
        this.verifier = employee;
        this.documentStatus = DocumentStatus.VERIFIED;
        this.verificationAt = new Date();

    }

    public void confirm(Employee conirmator) {

    }

    public void confirm(Employee confirmator, Employee forEmployee) {

    }

    public void archive(Employee employee){
        this.deleted = true;
        this.deletedBy = employee;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Employee getDeletedBy() {
        return deletedBy;
    }

    public void tag(Set<Tag> tags){
        this.tags = tags;
    }
    public Set<Tag> tags(){
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

    public DocumentNumber number() {
        return documentNumber;
    }

    public DocumentStatus status() {
        return documentStatus;
    }

    public Employee verifier() {
        return verifier;
    }

    public Date verificationAt() {
        return verificationAt;
    }
}
