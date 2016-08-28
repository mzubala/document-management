package pl.com.bottega.documentmanagement.domain;

import pl.com.bottega.documentmanagement.domain.events.DocumentListener;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static pl.com.bottega.documentmanagement.domain.DocumentStatus.*;

/**
 * Created by maciuch on 12.06.16.
 */
@Entity
public class Document {

    private static final int CHARS_PER_PAGE = 1000;

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

    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedAt;

    private boolean deleted;

    @ManyToOne
    private Employee deletedBy;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Tag> tags;

    @ManyToOne
    private Employee publishedBy;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private Set<Reader> readers = new HashSet<>();

    private BigDecimal printingCost;

    @Transient
    private Collection<DocumentListener> documentListeners = new HashSet<>();

    private Document() {}

    public Document(DocumentNumber documentNumber, String content, String title, Employee creator, PrintingCostCalculator printingCostCalculator) {
        this.number = documentNumber;
        this.content = content;
        this.title = title;
        this.creator = creator;
        this.documentStatus = DRAFT;
        this.createAt = new Date();
        this.deleted = false;
        this.printingCost = printingCostCalculator.calculate(pagesCount());
    }

    private int pagesCount() {
        return content.length() / CHARS_PER_PAGE + (content.length() % CHARS_PER_PAGE == 0 ? 0 : 1);
    }

    public void change(String title, String content) {
        checkArgument(!(title == null && content == null));
        this.title = title;
        this.content = content;
        this.documentStatus = DRAFT;
        this.updatedAt = new Date();
//        this.printingCost = printingCostCalculator.calculate(pagesCount());
    }

    public void verify(Employee employee) {
        checkArgument(employee != null);
        this.verificator = employee;
        this.documentStatus = VERIFIED;
        this.verifiedAt = new Date();
    }

    public void confirm(Employee confirmator) throws IllegalArgumentException {
        for (Iterator<Reader> it = readers.iterator(); it.hasNext();) {
            Reader reader = it.next();
            if (reader.equals(new Reader(this, confirmator))) {
                reader.confirm();
                return;
            }
        }
        throw new IllegalArgumentException("Reader has no access");
    }

    public void confirm(Employee confirmator, Employee forEmployee) throws IllegalArgumentException {
        for (Iterator<Reader> it = readers.iterator(); it.hasNext();) {
            Reader reader = it.next();
            if (reader.equals(new Reader(this, forEmployee))) {
                reader.confirm(confirmator);
                return;
            }
        }
        throw new IllegalArgumentException("Reader has no access");
    }

    public void publish(Employee employee, Collection<Employee> readers) {
        checkNotNull(employee);
        this.documentStatus = PUBLISHED;
        this.publishedBy = employee;
        this.publishedAt = new Date();
        this.readers = createReaders(readers);
    }

    private Set<Reader> createReaders(Collection<Employee> employees) {
        Set<Reader> readers = new HashSet<>();
        for (Employee e : employees)
            readers.add(new Reader(this, e));
        return readers;
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
        checkArgument(employee != null);
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

    public Date getPublishedAt() {
        return publishedAt;
    }

    public Set<Reader> getReaders() {
        return readers;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public Employee getPublishedBy() {
        return publishedBy;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void subscribeDocumentListener(DocumentListener documentListener) {
        documentListeners.add(documentListener);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        if (id != null ? !id.equals(document.id) : document.id != null) return false;
        return number != null ? number.equals(document.number) : document.number == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (number != null ? number.hashCode() : 0);
        return result;
    }
}

