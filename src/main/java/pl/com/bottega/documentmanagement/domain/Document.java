package pl.com.bottega.documentmanagement.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

/**
 * Created by maciuch on 12.06.16.
 */
@Entity
public class Document {

    @Id
    @GeneratedValue
    private Long id;

    private DocumentNumber documentNumber;
    private String content;
    private String title;
    private Boolean deleted;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt, verifiedAt, updatedAt, publishedAt;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    @ManyToOne(fetch = LAZY)
    private Employee creator;

    @ManyToOne(fetch = LAZY)
    private Employee verificator;

    @ManyToOne(fetch = LAZY)
    private Employee deleter;

    @ManyToOne(fetch = LAZY)
    private Employee publisher;

    @ManyToMany(cascade = ALL)
    private Set<Tag> tags;

    @OneToMany(cascade = ALL, mappedBy = "document", fetch = EAGER)
    private Set<Reader> readers;

    public Document() {
    }

    public Document(DocumentNumber documentNumber, String content, String title, Employee creator) {
        this.documentNumber = documentNumber;
        this.content = content;
        this.title = title;
        this.creator = creator;
        this.status = DocumentStatus.DRAFT;
        this.createdAt = new Date();
        this.deleted = false;
    }

    public Employee getPublisher() {
        return publisher;
    }

    public void setPublisher(Employee publisher) {
        this.publisher = publisher;
    }

    public Employee getDeleter() {
        return deleter;
    }

    public void setDeleter(Employee deleter) {
        this.deleter = deleter;
    }

    public void tag(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Employee getVerificator() {
        return verificator;
    }

    public String getContent() {
        return content;
    }

    public Employee getCreator() {
        return creator;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public DocumentNumber getNumber() {
        return documentNumber;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public Date getVerifiedAt() {
        return verifiedAt;
    }

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
        this.status = DocumentStatus.DRAFT;
        this.updatedAt = new Date();
    }

    public void verify(Employee employee) {
        checkArgument(employee != null);
        this.verificator = employee;
        this.status = DocumentStatus.VERIFIED;
        this.verifiedAt = new Date();
    }

    public void delete(Employee deleter) {
        this.deleter = deleter;
        this.deleted = true;
    }

    public void publish(Employee publisher, Iterable<Employee> employees) {
        checkNotNull(publisher);
        checkNotNull(employees);
        this.publisher = publisher;
        this.status = DocumentStatus.PUBLISHED;
        this.publishedAt = new Date();

        for (Employee employee : employees) {
            Reader reader = new Reader(this, employee);
            readers.add(reader);
        }
    }

    public void confirm(Employee confirmator) {
        confirm(confirmator, confirmator);
    }

    public void confirm(Employee targetEmployee, Employee confirmBy) {
        checkNotNull(targetEmployee);
        if (confirmBy == null)
            confirm(targetEmployee);

        Reader reader = getReader(targetEmployee);

        if (reader == null)
            throw new IllegalArgumentException("Document " + this.id + ". For employee id " + targetEmployee.getEmployeeId() + " confirming is forbidden");

        reader.confirm(confirmBy);
        readers.add(reader);
    }

    private Reader getReader(Employee targetEmployee) {
        for (Reader reader : readers) {
            if (reader.getDocument().equals(this) && reader.getTargetEmployee().equals(targetEmployee)) {
                if (reader.isConfirmed())
                    throw new IllegalArgumentException("Document " + id + ". For employee id " + targetEmployee.getEmployeeId() + " already confirmed");
                return reader;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        return documentNumber.equals(document.documentNumber);
    }

    @Override
    public int hashCode() {
        return documentNumber.hashCode();
    }

    public void setReaders(Set<Reader> readers) {
        this.readers = readers;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public Set<Reader> getReaders() {
        return readers;
    }
}
