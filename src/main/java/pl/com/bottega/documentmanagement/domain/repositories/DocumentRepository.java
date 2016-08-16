package pl.com.bottega.documentmanagement.domain.repositories;

import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.Reader;

/**
 * Created by maciuch on 12.06.16.
 */
public interface DocumentRepository {

    void save(Document document);

    Document load(DocumentNumber documentNumber);

}
