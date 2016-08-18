package pl.com.bottega.documentmanagement.api;

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

    private DocumentNumberGenerator documentNumberGenerator;
    private DocumentRepository documentRepository;
    private UserManager userManager;
    private DocumentFactory documentFactory;
    private EmployeeRepository employeeRepository;

    public DocumentFlowProcess(DocumentRepository documentRepository, UserManager userManager, DocumentNumberGenerator documentNumberGenerator,
                               DocumentFactory documentFactory, EmployeeRepository employeeRepository) {
        this.documentRepository = documentRepository;
        this.userManager = userManager;
        this.documentNumberGenerator = documentNumberGenerator;
        this.documentFactory = documentFactory;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    @RequiresAuth(roles = "EDITOR")
    public DocumentNumber create(String title, String content) {
        checkNotNull(title);
        checkNotNull(content);

        DocumentNumber documentNumber = documentNumberGenerator.generate();
        Document document = documentFactory.create(documentNumber, content, title, userManager.currentEmployee());

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
    @RequiresAuth(roles = "MANAGER")
    public void verify(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);

        Document document = documentRepository.load(documentNumber);
        document.verify(userManager.currentEmployee());
        documentRepository.save(document);
    }

    @Transactional
    @RequiresAuth(roles = "MANAGER")
    public void publish(DocumentNumber documentNumber, Set<Long> ids) {
        checkNotNull(documentNumber);

        Document document = documentRepository.load(documentNumber);
        Set<Reader> readers = addDocumentReaders(ids, document);
        document.publish(userManager.currentEmployee(), readers);
        documentRepository.save(document);
    }

    private Set<Reader> addDocumentReaders(Set<Long> ids, Document document) {
        Set<Reader> readers = new HashSet<>();
        for (Long id : ids) {
            EmployeeId employeeId = new EmployeeId(id);
            Employee employee = employeeRepository.findByEmployeeId(employeeId);
            if (employee == null)
                employee = createDigitalExcludedEmployee(employeeId);
            readers.add(new Reader(document, employee));
        }
        return readers;
    }

    private Employee createDigitalExcludedEmployee(EmployeeId employeeId) {
        userManager.signup(employeeId);
        return employeeRepository.findByEmployeeId(employeeId);
    }

    @Transactional
    @RequiresAuth(roles = "EDITOR")
    public void archive(DocumentNumber documentNumber) {
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
