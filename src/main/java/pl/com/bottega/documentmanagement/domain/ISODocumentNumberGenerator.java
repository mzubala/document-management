package pl.com.bottega.documentmanagement.domain;

import java.util.UUID;

/**
 * Created by paulina.pislewicz on 2016-06-18.
 */

public class ISODocumentNumberGenerator  implements DocumentNumberGenerator{
    @Override
    public DocumentNumber generate() {
        return new DocumentNumber("ISO-" + UUID.randomUUID().toString());
    }
}
