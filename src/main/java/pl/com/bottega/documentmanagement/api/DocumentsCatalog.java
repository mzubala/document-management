package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by bartosz.paszkowski on 12.06.2016.
 */
public class DocumentsCatalog {

    public Document get(DocumentNumber documentNumber){
        checkNotNull(documentNumber);
        return null;
    }

    public Iterable<Document> find(DocumentCriteria documentCriteria){
        checkNotNull(documentCriteria);

        return null;
    }


}
