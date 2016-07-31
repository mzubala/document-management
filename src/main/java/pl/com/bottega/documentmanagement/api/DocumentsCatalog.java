package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by Wojciech Winiarski on 10.07.2016.
 */
public interface DocumentsCatalog {
    DocumentDto get(DocumentNumber documentNumber);

    DocumentSearchResults find(DocumentCriteria documentCriteria);
}
