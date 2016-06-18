package pl.com.bottega.documentmanagement.domain;

import org.springframework.stereotype.Service;

/**
 * Created by Wojciech Winiarski on 18.06.2016.
 */

public class QEPDocumentNumberGenerator implements DocumentNumberGenerator {
    @Override
    public DocumentNumber generate() {
        return new DocumentNumber("QEP/" + (int) (Math.random()*100) + "/" + (int) (Math.random() * 100));
    }
}
