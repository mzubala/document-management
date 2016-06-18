package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentNumber;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Wojciech Winiarski on 12.06.16.
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
