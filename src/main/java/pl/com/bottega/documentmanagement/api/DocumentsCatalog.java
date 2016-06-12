package pl.com.bottega.documentmanagement.api;

import com.google.common.collect.Iterables;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by paulina.pislewicz on 2016-06-12.
 */
public class DocumentsCatalog {
    public Document get(DocumentNumber documentNumber){
        checkNotNull(documentNumber);
        return null;
    }
    public Iterable <Document> find (DocumentCriteria documentCriteria){
        checkNotNull(documentCriteria);
        return null;
    }

}

