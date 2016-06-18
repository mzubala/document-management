package pl.com.bottega.documentmanagement.domain;

/**
 * Created by Dell on 2016-06-18.
 */
public class QEPDocumentNumberGenerator implements DocumentNumberGenerator {

    @Override
    public DocumentNumber generate() {
        return new DocumentNumber("QEP/" + (int) Math.random() * 100 + "/" + (int) Math.random() * 100);
    }
}
