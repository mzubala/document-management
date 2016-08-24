package pl.com.bottega.documentmanagement.api;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.domain.*;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by maciuch on 12.06.16.
 */
@Service
public class DocumentFlowProcess {

    private DocumentFactory documentFactory;
    private DocumentRepository documentRepository;
    private EmployeeRepository employeeRepository;
    private UserManager userManager;
    private HRSystemFacade hrSystemFacade;
    private PrintSystemFacade printSystemFacade;
    private MailingFacade mailingFacade;

    public DocumentFlowProcess(DocumentRepository documentRepository, UserManager userManager,
                               DocumentFactory documentFactory, EmployeeRepository employeeRepository, HRSystemFacade hrSystemFacade, PrintSystemFacade printSystemFacade, MailingFacade mailingFacade) {
        this.documentRepository = documentRepository;
        this.userManager = userManager;
        this.documentFactory = documentFactory;
        this.employeeRepository = employeeRepository;
        this.hrSystemFacade = hrSystemFacade;
        this.printSystemFacade = printSystemFacade;
        this.mailingFacade = mailingFacade;
    }

    @Transactional
    @RequiresAuth(roles = "EDITOR")
    public DocumentNumber create(String title, String content) {
        checkNotNull(title);
        checkNotNull(content);

        Document document = documentFactory.create(title, content);
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
    public void publish(DocumentNumber documentNumber, Iterable<EmployeeId> ids) {
        checkNotNull(documentNumber);
        Document document = documentRepository.load(documentNumber);
        document.publish(userManager.currentEmployee(), getEmployees(ids));
        Set<EmployeeDetails> employeeDetailsSet = hrSystemFacade.getEmployeeDetails(Sets.newHashSet(ids));
        sendEmailsAboutPublishedDocument(document, employeeDetailsSet);
        printDocument(document, employeeDetailsSet);
    }

    private void sendEmailsAboutPublishedDocument(Document document, Set<EmployeeDetails> employeeDetailsSet) {
        Set<EmployeeDetails> employeeWithEmail = employeeDetailsSet.stream().filter(EmployeeDetails::hasEmail).collect(Collectors.toSet());
        mailingFacade.sendDocumentPublishedEmails(document, employeeWithEmail);
    }

    private void printDocument(Document document, Set<EmployeeDetails> employeeDetailsSet) {
        Set<EmployeeDetails> employeeWithoutEmail = employeeDetailsSet.stream().filter(employeeDetails -> !employeeDetails.hasEmail()).collect(Collectors.toSet());
        printSystemFacade.printDocument(document, employeeWithoutEmail);
    }



    private Set<Employee> getEmployees(Iterable<EmployeeId> ids) {
        Set<Employee> employees = employeeRepository.findByEmployeeIds(ids);
        ids.forEach((id) -> {
            if (!employees.stream().anyMatch((employee) -> employee.employeeId().equals(id)))
                employees.add(new Employee(id));
        });
        return employees;
    }

    @Transactional
    @RequiresAuth(roles = "MANAGER")
    public void archive(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        Document document = documentRepository.load(documentNumber);
        document.delete(userManager.currentEmployee());
        documentRepository.save(document);
    }

}
