package pl.com.bottega.documentmanagement.api;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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

    private DocumentNumberGenerator documentNumberGenerator;
    private DocumentRepository documentRepository;
    private EmployeeRepository employeeRepository;
    private UserManager userManager;
    private DocumentFactory documentFactory;
    private EmployeeFactory employeeFactory;

    public DocumentFlowProcess(DocumentRepository documentRepository,
                               UserManager userManager,
                               DocumentNumberGenerator documentNumberGenerator,
                               DocumentFactory documentFactory,
                               EmployeeRepository employeeRepository,
                               EmployeeFactory employeeFactory) {
        this.documentRepository = documentRepository;
        this.userManager = userManager;
        this.documentNumberGenerator = documentNumberGenerator;
        this.documentFactory = documentFactory;
        this.employeeRepository = employeeRepository;
        this.employeeFactory = employeeFactory;
    }

    @Transactional
    //@RequiresAuth(roles = "EDITOR")
    public DocumentNumber create(String title, String content) {
        checkNotNull(title);
        checkNotNull(content);

        DocumentNumber documentNumber = documentNumberGenerator.generate();
        Document document = documentFactory.create(documentNumber, title, content, userManager.currentEmployee());
        documentRepository.save(document);
        return documentNumber;
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
    // @RequiresAuth(roles = "MANAGER")
    public void verify(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);

        Document document = documentRepository.load(documentNumber);
        document.verify(userManager.currentEmployee());
        documentRepository.save(document);
    }

    @Transactional
    //@RequiresAuth(roles = "MANAGER")
    public void publish(DocumentNumber documentNumber, Set<EmployeeId> targetEmployees) {
        checkNotNull(documentNumber);
        Document document = documentRepository.load(documentNumber);
        document.publish(userManager.currentEmployee(), createUnregisteredEmployee(targetEmployees));
        documentRepository.save(document);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Set<Employee> createUnregisteredEmployee(Set<EmployeeId> targetEmployees) {
        Set<Employee> result = new HashSet<>();
        for (EmployeeId employeeId : targetEmployees) {
            Employee loadedEmployee = employeeRepository.findByEmployeeId(employeeId);
            if (loadedEmployee != null) {
                result.add(loadedEmployee);
            } else {
                Employee newUnregisteredEmployee = employeeFactory.create(employeeId);
                employeeRepository.save(newUnregisteredEmployee);
                result.add(newUnregisteredEmployee);
            }
        }
        return result;
    }

    @Transactional
    @RequiresAuth(roles = "MANAGER")
    public void delete(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        Document document = documentRepository.load(documentNumber);
        document.delete(userManager.currentEmployee());
        documentRepository.save(document);
    }

    public DocumentNumber createNewVersion(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);

        return null;
    }

}
