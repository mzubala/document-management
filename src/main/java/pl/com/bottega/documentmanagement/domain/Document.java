package pl.com.bottega.documentmanagement.domain;

import javax.persistence.*;
import java.util.*;

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
    private Employee employeeWhoDelete;
    @ManyToOne
    private Employee publisher;
    @OneToMany (mappedBy = "document", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set <Reader> readers = new HashSet<>();
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

    public void publish(Employee publisher, Collection<Employee> readers) {
//        checkArgument(readers !=null && readers.size() >0);
//        checkState(documentStatus.equals(DocumentStatus.VERIFIED));

      /*  Set<Reader> newReaders = readers.stream().map(employee -> new Reader(this, readers)).collect(Collectors.toSet());
        setReaders(newReaders);*/
        this.publisher = publisher;
        documentStatus = documentStatus.PUBLISHED;
        this.publishedAt = new Date();
        //this.readers = createReaders(readers);
    }

    private void setReaders(Set<Reader> newReaders) {
        if(readers==null)
            new HashSet<>();
    }

  /*  private Set<Reader> createReaders(Collection<Employee> employees) {
        Set<Reader> readers = new HashSet<>();
        for(Employee e: employees)
            readers.add(new Reader(this, e));
        return readers;
    }*/

/*    public void confirm(Employee confirmator) {
        checkArgument(confirmator != null);

        Employee employeeEligibleToConfirm;
        for (Reader reader : readers) {
            employeeEligibleToConfirm =  reader.getEmployee();
            if (employeeEligibleToConfirm.equals(confirmator)) {
                reader.confirm();
            } else {
                throw new IllegalArgumentException("Employee is not entitled to read document!");
            }
        }
    }
    public void confirm(Employee confirmator, Employee forEmployee) {
        checkArgument(confirmator!=null);
        checkArgument(forEmployee!=null);

        Employee employeeEligibleToConfirm;
        for (Reader reader : readers) {
            employeeEligibleToConfirm =  reader.getEmployee();
            if (employeeEligibleToConfirm.equals(confirmator)) {
                reader.confirmedBy= forEmployee;
            } else {
                throw new IllegalArgumentException("Employee is not entitled to read document!");
            }
        }
    }*/

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

    public void delete() {
        this.deleted = true;
        this.employeeWhoDelete = employeeWhoDelete;

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
