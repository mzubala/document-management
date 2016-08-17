package pl.com.bottega.documentmanagement.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.domain.*;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by maciuch on 12.06.16.
 */
@Service
public class DocumentFlowProcess {

    private DocumentNumberGenerator documentNumberGenerator;
    private DocumentRepository documentRepository;
    private UserManager userManager;
    private EmployeeRepository employeeRepository;
    private DocumentFactory documentFactory;

    @Autowired
    public DocumentFlowProcess(DocumentRepository documentRepository, DocumentFactory documentFactory, UserManager userManager, DocumentNumberGenerator documentNumberGenerator) {
        this.documentNumberGenerator = documentNumberGenerator;
        this.documentFactory = documentFactory;
        this.documentRepository = documentRepository;
        this.userManager = userManager;
    }

    @Transactional
   // @RequiresAuth(roles = { "EDITOR"})
    public DocumentNumber create(String title, String content) {
        checkNotNull(title);
        checkNotNull(content);

        Document document = documentFactory.create( documentNumberGenerator.generate(),content, title, userManager.currentEmployee());
        documentRepository.save(document);
        return document.number();
    }

    @Transactional
    //@RequiresAuth(roles = {"EDITOR"})
    public void change(DocumentNumber documentNumber, String newTitle, String newContent) {
        checkNotNull(documentNumber);
        checkNotNull(newTitle);
        checkNotNull(newContent);

        Document document = documentRepository.load(documentNumber);
        document.change(newTitle, newContent);
        documentRepository.save(document);
    }
    @Transactional
   // @RequiresAuth(roles = {"MANAGER"})
    public void verify(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);

        Document document = documentRepository.load(documentNumber);
        document.verify(userManager.currentEmployee());
        documentRepository.save(document);
    }

    @Transactional
    @RequiresAuth(roles = {"MANAGER"})
    public void publish(DocumentNumber documentNumber, Iterable<EmployeeId> ids) {
        checkNotNull(documentNumber);
        Document document = documentRepository.load(documentNumber);
       // document.confirm(ids);

    }

    @Transactional
    @RequiresAuth(roles = {"EDITOR"})
    public void delete(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        Document document = documentRepository.load(documentNumber);
        document.delete();
        documentRepository.save(document);
    }

    @Transactional
    @RequiresAuth(roles = {"EDITOR"})
    public void archive(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        Document document = documentRepository.load(documentNumber);
        documentRepository.save(document);
    }

    public DocumentNumber createNewVersion(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);

        return null;
    }

}
