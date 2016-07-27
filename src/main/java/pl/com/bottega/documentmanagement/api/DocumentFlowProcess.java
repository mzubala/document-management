package pl.com.bottega.documentmanagement.api;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.DocumentNumberGenerator;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by maciuch on 12.06.16.
 */
@Service
public class DocumentFlowProcess {

    private DocumentNumberGenerator documentNumberGenerator;
    private DocumentRepository documentRepository;
    private UserManager userManager;

    public DocumentFlowProcess(DocumentRepository documentRepository, UserManager userManager, DocumentNumberGenerator documentNumberGenerator) {
        this.documentRepository = documentRepository;
        this.userManager = userManager;
        this.documentNumberGenerator = documentNumberGenerator;
    }

    @Transactional
    @RequiresAuth(roles = {"EDITOR"})
    public DocumentNumber create(String title, String content) {
        checkNotNull(title);
        checkNotNull(content);

        DocumentNumber documentNumber = documentNumberGenerator.generate();
        Document document = new Document(documentNumber, title, content, userManager.currentEmployee());
        documentRepository.save(document);

        return documentNumber;
    }

    public Document load(DocumentNumber documentNumber) {
        return documentRepository.load(documentNumber);
    }

    @Transactional
    @RequiresAuth(roles = {"EDITOR"})
    public void change(DocumentNumber documentNumber, String newTitle, String newContent) {
        checkNotNull(documentNumber);
        checkNotNull(newTitle);
        checkNotNull(newContent);

        Document document = documentRepository.load(documentNumber);
        document.change(newTitle, newContent);
        documentRepository.save(document);
    }

    @Transactional
    @RequiresAuth(roles = {"MANAGER"})
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
    }

    @Transactional
    @RequiresAuth(roles = {"MANAGER"})
    public void archive(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
    }

    @Transactional
    @RequiresAuth(roles = {"EDITOR"})
    public void delete(DocumentNumber documentNumber){
        checkNotNull(documentNumber);
        Document document = documentRepository.load(documentNumber);
        document.delete();
        documentRepository.save(document);
    }

    public DocumentNumber createNewVersion(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);

        return null;
    }
}
