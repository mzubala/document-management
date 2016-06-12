package pl.com.bottega.documentmanagement.domain.repositories;

import pl.com.bottega.documentmanagement.domain.DocumentCriteria;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by paulina.pislewicz on 2016-06-12.
 */
public interface DocumentRepository {
    void save(Document document);
    Document load (DocumentNumber documentNumber);

    Iterable<Document> find (DocumentCriteria criteria);
}
