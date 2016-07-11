package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentNumber;

import java.util.List;

/**
 * Created by paulina.pislewicz on 2016-07-10.
 */
public interface DocumentsCatalog {
    pl.com.bottega.documentmanagement.infrastructure.DocumentDto get(DocumentNumber documentNumber);

    List<pl.com.bottega.documentmanagement.infrastructure.DocumentDto> find(DocumentCriteria documentCriteria);
}
