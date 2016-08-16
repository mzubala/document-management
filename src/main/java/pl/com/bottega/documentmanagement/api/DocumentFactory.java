package pl.com.bottega.documentmanagement.api;

import org.springframework.stereotype.Service;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.Employee;

/**
 * Created by Admin on 13.08.2016.
 */
@Service
public class DocumentFactory {

    public DocumentFactory() {}

    public Document create(DocumentNumber documentNumber, String title, String content, Employee creator) {
        return new Document(documentNumber, content, title, creator);
    }
}
