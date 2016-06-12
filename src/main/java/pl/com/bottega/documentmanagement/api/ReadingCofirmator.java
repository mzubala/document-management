package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by bartosz.paszkowski on 12.06.2016.
 */
public class ReadingCofirmator {

    private DocumentRepository documentRepository;
    private UserManager userManager;

    public void confirm(DocumentNumber documentNumber){
        checkNotNull(documentNumber);

        Document document = documentRepository.load(documentNumber);
        document.confirm(userManager.currentEmployee());
        documentRepository.save(document);

    }

    public void confirm(DocumentNumber documentNumber, Employee forEmplyee){
        checkNotNull(documentNumber);
        checkNotNull(forEmplyee);

        Document document = documentRepository.load(documentNumber);
        document.confirm(userManager.currentEmployee(), forEmplyee);
        documentRepository.save(document);
    }

}
