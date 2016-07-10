package pl.com.bottega.documentmanagement.domain;



import java.util.UUID;

/**
 * Created by Wojciech Winiarski on 18.06.2016.
 */

public class ISODocumentNumberGenerator implements DocumentNumberGenerator {
    @Override
    public DocumentNumber generate() {

        return new DocumentNumber("ISO-" + UUID.randomUUID().toString());
    }
}
