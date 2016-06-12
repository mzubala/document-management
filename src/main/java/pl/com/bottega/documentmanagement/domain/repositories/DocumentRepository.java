package pl.com.bottega.documentmanagement.domain.repositories;

import pl.com.bottega.documentmanagement.domain.DocumentCriteria;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by bartosz.paszkowski on 12.06.2016.
 */
public interface DocumentRepository {

    void save(Document document);

    Document load(DocumentNumber documentNumber);


    Iterable<Document> find(DocumentCriteria documentCriteria);
}
