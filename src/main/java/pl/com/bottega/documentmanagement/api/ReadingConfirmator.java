package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.repositiores.DocumentRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dell on 2016-06-12.
 */
public class ReadingConfirmator {

    private DocumentRepository documentRepository;
    private UserManager userManager;

    public void confirm(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        Document document = documentRepository.load(documentNumber);
        document.confirm(userManager.currentEmployee());
        documentRepository.save(document);
    }

    public void confirm(DocumentNumber documentNumber, Employee forEmployee) {
        checkNotNull(documentNumber);
        checkNotNull(forEmployee);

        Document document = documentRepository.load(documentNumber);
        document.confirm(userManager.currentEmployee(), forEmployee);
        documentRepository.save(document);
    }
}
