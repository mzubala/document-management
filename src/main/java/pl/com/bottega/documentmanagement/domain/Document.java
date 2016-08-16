package pl.com.bottega.documentmanagement.domain;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.Date;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
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

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Temporal(TemporalType.TIMESTAMP)
    private Date verifiedAt;

    /**
     * 'true' if document is deleted
     */
    private boolean deleted;

    @ManyToOne
    private Employee deletedBy;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Tag> tags;

    private Document() {}

    public Document(DocumentNumber documentNumber, String content, String title, Employee creator) {
        this.number = documentNumber;
        this.content = content;
        this.title = title;
        this.creator = creator;
        this.documentStatus = DRAFT;
        this.createAt = new Date();
        this.deleted = false;
    }

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
        this.documentStatus = DRAFT;
        this.updatedAt = new Date();
    }

    public void verify(Employee employee) {
        checkArgument(employee != null);
        this.verificator = employee;
        this.documentStatus = VERIFIED;
        this.verifiedAt = new Date();
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

    public Long getId() {
        return id;
    }

    public Employee getCreator() {
        return creator;
    }

    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    public Employee getVerificator() {
        return verificator;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getVerifiedAt() {
        return verifiedAt;
    }

    public void delete(Employee employee) {
        this.deleted = true;
        this.deletedBy = employee;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public Employee getDeletedBy() {
        return deletedBy;
    }

    public void tag(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Tag> tags() {
        return tags;
    }
}

