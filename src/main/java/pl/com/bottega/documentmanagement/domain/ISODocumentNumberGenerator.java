package pl.com.bottega.documentmanagement.domain;

import com.sun.xml.internal.ws.developer.Serialization;
import org.springframework.stereotype.Service;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;

import java.util.Random;
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
