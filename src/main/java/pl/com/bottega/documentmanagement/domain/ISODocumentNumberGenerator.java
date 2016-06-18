package pl.com.bottega.documentmanagement.domain;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Dell on 2016-06-18.
 */
public class ISODocumentNumberGenerator implements DocumentNumberGenerator {

    @Override
    public DocumentNumber generate() {
        return new DocumentNumber("ISO - " + UUID.randomUUID().toString());
    }
}
