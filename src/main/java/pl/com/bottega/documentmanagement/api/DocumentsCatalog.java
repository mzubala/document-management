package pl.com.bottega.documentmanagement.api;

import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by Dell on 2016-07-10.
 */
public interface DocumentsCatalog {
    @Transactional
    DocumentDto get(DocumentNumber documentNumber);

    Iterable<DocumentDto> find(DocumentCriteria documentCriteria);
}
