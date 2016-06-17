package pl.com.bottega.documentmanagement.api;

import com.google.common.collect.Iterables;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentCriteria;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by maciuch on 12.06.16.
 */
public class DocumentsCatalog {

    public DocumentDto get(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);

        return null;
    }

    public Iterable<DocumentDto> find(DocumentCriteria documentCriteria) {
        checkNotNull(documentCriteria);

        return null;
    }

}
