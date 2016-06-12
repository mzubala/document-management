package pl.com.bottega.documentmanagement.api;

import com.google.common.collect.Iterables;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentCriteria;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.repositiores.DocumentRepository;
import pl.com.bottega.documentmanagement.infrastructure.DocumentDto;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dell on 2016-06-12.
 */
public class DocumentsCatalog {

    private DocumentRepository documentRepository;

    public DocumentDto get(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        Document document = documentRepository.load(documentNumber);

        return document.export();
    }

    public Iterable<DocumentDto> find(DocumentCriteria documentCriteria) {
        checkNotNull(documentCriteria);
        Iterable<Document> documents = documentRepository.find(documentCriteria);

//        return Iterables.transform(documents, (document) -> document.export());
        return Iterables.transform(documents, Document::export);
    }
}
