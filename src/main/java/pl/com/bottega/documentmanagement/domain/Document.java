package pl.com.bottega.documentmanagement.domain;

import pl.com.bottega.documentmanagement.domain.events.DocumentListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by maciuch on 12.06.16.
 */
@Entity
public class Document {

    private static final int CHARS_PER_PAGE = 1000;

    @Id
    @GeneratedValue
    private Long id;

    private DocumentNumber documentNumber;

    private String content;

    private String title;

    private boolean deleted;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt, verifiedAt, updatedAt;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee creator;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee verificator;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee deletor;

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Tag> tags;

    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reader> readers;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee publisher;

    private Date publishedAt;

    private BigDecimal printingCost;

    @Transient
    private Collection<DocumentListener> documentListeners = new HashSet<>();

    private Document() {
    }

    public Document(DocumentNumber documentNumber, String content, String title, Employee creator,
                    PrintCostCalculator printCostCalculator
    ) {
        this.documentNumber = documentNumber;
        this.content = content;
        this.title = title;
        this.creator = creator;
        this.status = DocumentStatus.DRAFT;
        this.createdAt = new Date();
        this.deleted = false;
        this.printingCost = printCostCalculator.cost(pagesCount());
    }

    private int pagesCount() {
        return content.length() / CHARS_PER_PAGE +
                (content.length() % CHARS_PER_PAGE == 0 ? 0 : 1);
        //return Math.ceil((double) content.length()) / CHARS_PER_PAGE);
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

    public void confirm(Employee confirmator) {
        Reader reader = reader(confirmator);
        reader.confirm();
    }

    public void confirm(Employee confirmator, Employee forEmployee) {
        Reader reader = reader(forEmployee);
        reader.confirm(confirmator);
    }

    public void delete(Employee deletor) {
        this.deletor = deletor;
        this.deleted = true;
    }

    public void export(DocumentBuilder builder) {
        builder.start();
        builder.addTitle(title);
        builder.addContent(content);
        builder.addCreatedAt(createdAt);
        builder.addStatus(status.name());
        builder.end();
    }

    public void tag(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Tag> tags() {
        return tags;
    }

    public Employee verificator() {
        return verificator;
    }

    public String content() {
        return content;
    }

    public Employee creator() {
        return creator;
    }

    public String title() {
        return title;
    }

    public boolean deleted() {
        return deleted;
    }

    public DocumentNumber number() {
        return documentNumber;
    }

    public DocumentStatus status() {
        return status;
    }

    public Date verifiedAt() {
        return verifiedAt;
    }

    public Date updatedAt() {
        return updatedAt;
    }

    public Employee deletor() {
        return deletor;
    }

    public void publish(Employee publisher, Set<Employee> employees) {
        checkArgument(employees != null && employees.size() > 0);
        checkState(status.equals(DocumentStatus.VERIFIED));
        Set<Reader> newReaders = employees.stream().map((employee) -> new Reader(this, employee)).collect(Collectors.toSet());
        setReaders(newReaders);
        this.publishedAt = new Date();
        this.publisher = publisher;
        this.status = DocumentStatus.PUBLISHED;
        notifyDocumentPublished();
    }

    private void notifyDocumentPublished() {
        for (DocumentListener listener : documentListeners)
            listener.published(this);
        //eventPublisher.publish(new DocumentPublishedEvent(number))
    }

    private void setReaders(Set<Reader> newReaders) {
        if (readers == null)
            readers = new HashSet<>();
        else
            readers.clear();
        this.readers.addAll(newReaders);
    }

    public Set<Reader> readers() {
        return Collections.unmodifiableSet(readers);
    }

    public Employee publisher() {
        return publisher;
    }

    public Date publishedAt() {
        return publishedAt;
    }

    public Reader reader(Employee employee) {
        return readers().stream().
                filter((reader) -> reader.concernsEmployee(employee)).
                findFirst().orElseThrow(() -> new IllegalArgumentException());
    }

    public void subscribeDocumentListener(DocumentListener documentListener) {
        documentListeners.add(documentListener);
    }

}
