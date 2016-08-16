package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by Dell on 2016-07-10.
 */
public interface DocumentsCatalog {

    DocumentDto get(DocumentNumber documentNumber);

    DocumentSearchResults find(DocumentCriteria documentCriteria);
}
