package pl.com.bottega.documentmanagement.controller;

import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by paulina.pislewicz on 2016-07-05.
 */
public class VerificationRequest {
    public DocumentNumber getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(DocumentNumber documentNumber) {
        this.documentNumber = documentNumber;
    }

    DocumentNumber documentNumber;

}
