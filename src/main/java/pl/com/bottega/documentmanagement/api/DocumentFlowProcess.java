package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.DocumentNumberGenerator;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.repositries.DocumentRepository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Wojciech Winiarski on 12.06.2016.
 */
public class DocumentFlowProcess implements DocumentNumberGenerator{

    private DocumentNumberGenerator documentNumberGenerator;
    private DocumentRepository documentRepository;
    private UserManager userService;

    public DocumentNumber create(String title, String content){

        checkNotNull(title);
        checkNotNull(content);
        DocumentNumber documentNumber =  documentNumberGenerator.generate();
        Document document = new Document(documentNumber, title, content);
        documentRepository.save(document);

        return documentNumber;
    }

    public void change(DocumentNumber documentNumber, String newTitle, String newContent){
        checkNotNull(documentNumber);
        checkNotNull(newContent);
        checkNotNull(newTitle);

        Document document = documentRepository.load(documentNumber);
        document.change(newTitle, newContent);
        documentRepository.save(document);


    }

    public void verify(DocumentNumber documentNumber){

        checkNotNull(documentNumber);
        Document document = documentRepository.load(documentNumber);
        document.verify(userService.currentEmployee());
        documentRepository.save(document);


    }
    public void publish(DocumentNumber documentNumber, Iterable<EmployeeId> ids){
        checkNotNull(documentNumber);

    }
    public void archive(DocumentNumber  documentNumber){

        checkNotNull(documentNumber);
    }

    public DocumentNumber CreateNewVersion(DocumentNumber documentNumber){
        checkNotNull(documentNumber);

        return null;
    }

    @Override
    public DocumentNumber generate() {
        return null;
    }
}
