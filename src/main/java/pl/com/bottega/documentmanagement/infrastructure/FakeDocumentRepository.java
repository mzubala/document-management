package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Repository;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;

/**
 * Created by paulina.pislewicz on 2016-06-18.
 */

@Repository
public class FakeDocumentRepository implements DocumentRepository {
    @Override
    public void save(Document document) {
        System.out.println("Save dokument");
    }

    @Override
    public Document load(DocumentNumber documentNumber) {
        System.out.println("Load document");
        return null;
    }
}
