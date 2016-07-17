package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by paulina.pislewicz on 2016-07-10.
 */
public interface DocumentsCatalog {
    pl.com.bottega.documentmanagement.api.DocumentDto get(DocumentNumber documentNumber);

    Iterable <pl.com.bottega.documentmanagement.api.DocumentDto> find(DocumentCriteria documentCriteria);
}
