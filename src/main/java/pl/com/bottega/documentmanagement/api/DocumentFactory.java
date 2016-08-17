package pl.com.bottega.documentmanagement.api;

import org.springframework.stereotype.Service;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.Employee;

/**
 * Created by paulina.pislewicz on 2016-08-16.
 */
@Service
public class DocumentFactory {

    public DocumentFactory(){
    }

    public Document create (DocumentNumber documentNumber, String content, String title, Employee creator){
        Document document = new Document(documentNumber, content, title, creator);
        return document;
    }
}
