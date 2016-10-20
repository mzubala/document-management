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
    private Long documentId;
    @Embedded
    private DocumentNumber documentNumber;
    private String content;
    private String title;

    @Enumerated(EnumType.STRING)
    private DocumentStatus documentStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee creator;
    @ManyToOne(fetch = FetchType.LAZY)
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
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee deletedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date publishedAt;
    @ManyToOne
    private Employee publisher;

    @ManyToMany(cascade = CascadeType.ALL) //zapis dokumentu pociagnie zapis tagów
    private Set<Tag> tags;
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reader> readers;

    private BigDecimal printingCost;

    @Transient // ta adnotacja mówi że obiekt jest ulotnyi nie powinno się mapować do bazy danych
    private Collection<DocumentListener> documentListeners = new HashSet<>();

    public Document(){}

    public Document(DocumentNumber documentNumber, String content, String title, Employee creator,
                    PrintCostCalculator printCostCalculator) {
        this.documentNumber = documentNumber;
        this.content = content;
        this.title = title;
        this.creator = creator;
        this.documentStatus = DocumentStatus.DRAFT;
        this.createdAt = new Date();
        this.deleted = false;
        this.printingCost = printCostCalculator.cost(pagesCount());

    }
    private int pagesCount() {
        return content.length() / CHARS_PER_PAGE + content.length()% CHARS_PER_PAGE == 0 ? 0:1;
        //return  Math.ceil((double)content.length()/CHARS_PER_PAGE);
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

    public void confirm(Employee confirmator) {
        Reader reader = reader(confirmator);
        reader.confirm();
    }

    public void confirm(Employee confirmator, Employee forEmployee) {
        Reader reader = reader(forEmployee);
        reader.confirm(confirmator);
    }

    public void archive(Employee employee){
        this.deleted = true;
        this.deletedBy = employee;
    }

    public void publish(Employee publisher, Set<Employee> employees){
        checkArgument(employees != null && employees.size() > 0);
        checkState(documentStatus.equals(DocumentStatus.VERIFIED));
        Set<Reader> newReaders = employees.stream().map((employee) -> new Reader(this, employee)).collect(Collectors.toSet());
        setReaders(newReaders);
        this.publishedAt = new Date();
        this.publisher = publisher;
        this.documentStatus = DocumentStatus.PUBLISHED;
        notifyDocumentPublished();
    }


    public Set<Reader> readers() {
        return Collections.unmodifiableSet(readers);
    }


    public Reader reader(Employee employee) {
        return readers().stream().
                filter((reader) -> reader.concernsEmployee(employee)).
                findFirst().orElseThrow(() -> new IllegalArgumentException());
    }

    public void subscribeDocumentListener(DocumentListener documentListener){
        documentListeners.add(documentListener);
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

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Employee getPublisher() {
        return publisher;
    }

    public void setPublisher(Employee publisher) {
        this.publisher = publisher;
    }

    public void export(DocumentBuilder builder){
        builder.start();
        builder.addTitle(title);
        builder.addContent(content);
        builder.addCreatedAt(createdAt);
        builder.addStatus(documentStatus.name());
        builder.end();

    }


    private void notifyDocumentPublished() {
        for (DocumentListener listener : documentListeners){
            listener.published(this);
        }
    }
    private void setReaders(Set<Reader> newReaders) {
        if(readers == null)
            readers = new HashSet<>();
        else
            readers.clear();
        this.readers.addAll(newReaders);
    }
}
