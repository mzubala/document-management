package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by maciuch on 10.07.16.
 */
public interface DocumentsCatalog {
    pl.com.bottega.documentmanagement.api.DocumentDto get(DocumentNumber documentNumber);

    DocumentSearchResults find(DocumentCriteria documentCriteria);
}
