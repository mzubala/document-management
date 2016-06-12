package pl.com.bottega.documentmanagement.domain.repositiores;

import pl.com.bottega.documentmanagement.domain.DocumentCriteria;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by Dell on 2016-06-12.
 */
public interface DocumentRepository {

    Document load(DocumentNumber documentNumber);
    void save(Document document);
    Iterable<Document> find(DocumentCriteria documentCriteria);
}
