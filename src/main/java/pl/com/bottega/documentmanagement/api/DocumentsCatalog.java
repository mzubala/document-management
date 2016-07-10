package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by maciuch on 10.07.16.
 */
public interface DocumentsCatalog {
    DocumentDto get(DocumentNumber documentNumber);

    Iterable<DocumentDto> find(DocumentCriteria documentCriteria);
}
