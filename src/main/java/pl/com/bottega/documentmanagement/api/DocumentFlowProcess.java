package pl.com.bottega.documentmanagement.api;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.domain.*;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by maciuch on 12.06.16.
 */
@Service

public class DocumentFlowProcess {

    private MailingFacade mailingFacade;
    private PrintSystemFacade printSystemFacade;
    private HRSystemFacade hrSystemFacade;
    private DocumentFactory documentFactory;
    //@Autowired
    private DocumentNumberGenerator documentNumberGenerator;
    //@Autowired
    private DocumentRepository documentRepository;
    //@Autowired
    private UserManager userManager;
    private EmployeeRepository employeeRepository;

    @Autowired
    public DocumentFlowProcess(DocumentNumberGenerator documentNumberGenerator, DocumentRepository documentRepository,
                               UserManager userManager, DocumentFactory documentFactory, EmployeeRepository employeeRepository,
                               HRSystemFacade hrSystemFacade, PrintSystemFacade printSystemFacade, MailingFacade mailingFacade) {
        this.documentNumberGenerator = documentNumberGenerator;
        this.documentRepository = documentRepository;
        this.userManager = userManager;
        this.documentFactory = documentFactory;
        this.employeeRepository = employeeRepository;
        this.hrSystemFacade = hrSystemFacade;
        this.printSystemFacade = printSystemFacade;
        this.mailingFacade = mailingFacade;
    }

    @Transactional //przed rozpoczęciem metody jest otwierana tranzakcja po zakończeniu metody tranzakcja jest zatwierdzana
    @RequiresAuth(roles = "EDITOR")
    public DocumentNumber create(String title, String content) {
        checkNotNull(title);
        checkNotNull(content);

        Document document = documentFactory.create( title, content);
        documentRepository.save(document);
        return document.number();
    }

    @Transactional
    @RequiresAuth(roles = "EDITOR")
    public void change(DocumentNumber documentNumber, String newTitle, String newContent) {
        checkNotNull(documentNumber);
        checkNotNull(newTitle);
        checkNotNull(newContent);

        Document document = documentRepository.load(documentNumber);
        document.change(newTitle, newContent);
        documentRepository.save(document);
    }
    @Transactional
    @RequiresAuth(roles = "MANAGER")
    public void verify(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);

        Document document = documentRepository.load(documentNumber);
        document.verify(userManager.currentEmployee());
        documentRepository.save(document);
    }

    @Transactional
    @RequiresAuth(roles = "MANAGER")
    public void archive(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        Document document = documentRepository.load(documentNumber);
        document.archive(userManager.currentEmployee());
        documentRepository.save(document);
    }

    @Transactional
    @RequiresAuth(roles = "MANAGER")
    public void publish(DocumentNumber documentNumber, Iterable<EmployeeId> ids){
        checkNotNull(documentNumber);
        Document document = documentRepository.load(documentNumber);
        document.publish(userManager.currentEmployee(),getEmployees(ids));
        Set<EmployeeDetails> employeeDetailsSet = hrSystemFacade.getEmployeeDetails(Sets.newHashSet(ids));
        sendEmailAboutPublishDocument(document,employeeDetailsSet);
        printDocument(document, employeeDetailsSet);
    }

    private void sendEmailAboutPublishDocument(Document document, Set<EmployeeDetails> employeeDetailsSet){
        Set<EmployeeDetails>employeeWithMail = new HashSet<>();
        for (EmployeeDetails employeeDetail : employeeDetailsSet){
            if (employeeDetail.getEmail() != null)
                employeeWithMail.add(employeeDetail);
        }
        mailingFacade.sendDocumentPublishedEmails(document, employeeWithMail);
    }

    private void printDocument (Document document, Set<EmployeeDetails> employeeDetailsSet){
        Set<EmployeeDetails> employeeWithoutMail = new HashSet<>();
        for (EmployeeDetails employeeDetail : employeeDetailsSet){
            if (employeeDetail.getEmail() == null)
                employeeWithoutMail.add(employeeDetail);
        }
        printSystemFacade.printDocument(document, employeeWithoutMail);
    }

    private Set<Employee> getEmployees(Iterable<EmployeeId> ids){
        Set<Employee> employees = employeeRepository.findByEmployeeIds(ids);
        ids.forEach((id)->{
            if (!employees.stream().anyMatch((employee)->employee.employeeId().equals(id)))
                employees.add(new Employee(id));
        });
        return employees;
    }

    @Transactional
    public DocumentNumber createNewVersion(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);

        return null;
    }
}
