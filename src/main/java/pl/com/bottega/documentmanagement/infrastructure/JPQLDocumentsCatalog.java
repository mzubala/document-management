package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.DocumentCriteria;
import pl.com.bottega.documentmanagement.api.DocumentDto;
import pl.com.bottega.documentmanagement.api.DocumentSearchResults;
import pl.com.bottega.documentmanagement.api.DocumentsCatalog;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Seta on 2016-07-28.
 */
//@Component
public class JPQLDocumentsCatalog implements DocumentsCatalog {
    //  "SELECTS new ...DocumentDto("+
    //        "d.title, d.content, d.status....."+
    //      "FROM Document d"+
    //    "WHERE() AND ()"; HSQL korzystanie z innego api wylaczyc @componet


  //  @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DocumentDto get(DocumentNumber documentNumber) {
    checkNotNull(documentNumber);
    return null;
    }

    @Override
    public DocumentSearchResults find(DocumentCriteria documentCriteria) {
        return null;
    }
}
