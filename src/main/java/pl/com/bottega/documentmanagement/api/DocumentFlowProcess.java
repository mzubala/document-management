package pl.com.bottega.documentmanagement.api;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.domain.*;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import java.util.Set;

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

    public DocumentFlowProcess(DocumentRepository documentRepository, UserManager userManager,
                               DocumentFactory documentFactory, EmployeeRepository employeeRepository) {
        this.documentRepository = documentRepository;
        this.userManager = userManager;
        this.documentFactory = documentFactory;
        this.employeeRepository = employeeRepository;
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
