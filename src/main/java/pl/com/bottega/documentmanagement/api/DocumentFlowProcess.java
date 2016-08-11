package pl.com.bottega.documentmanagement.api;

import org.springframework.beans.factory.annotation.Autowired;
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
    //@Autowired
    private DocumentNumberGenerator documentNumberGenerator;
    //@Autowired
    private DocumentRepository documentRepository;
    //@Autowired
    private UserManager userManager;

    @Autowired
    public DocumentFlowProcess(DocumentNumberGenerator documentNumberGenerator, DocumentRepository documentRepository, UserManager userManager) {
        this.documentNumberGenerator = documentNumberGenerator;
        this.documentRepository = documentRepository;
        this.userManager = userManager;
    }

    @Transactional //przed rozpoczęciem metody jest otwierana tranzakcja po zakończeniu metody tranzakcja jest zatwierdzana
    @RequiresAuth(roles = "EDITOR")
    public DocumentNumber create(String title, String content) {
        checkNotNull(title);
        checkNotNull(content);

        DocumentNumber documentNumber = documentNumberGenerator.generate();
        Document document = new Document(documentNumber, title, content, userManager.currentEmployee());
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
    public void publish(DocumentNumber documentNumber, Iterable<EmployeeId> ids) {
        checkNotNull(documentNumber);
    }

    @Transactional
    @RequiresAuth(roles = "EDITOR")
    public void archive(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        Document document = documentRepository.load(documentNumber);
        document.archive(userManager.currentEmployee());
        documentRepository.save(document);

    }

    @Transactional
    public DocumentNumber createNewVersion(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);

        return null;
    }

}
