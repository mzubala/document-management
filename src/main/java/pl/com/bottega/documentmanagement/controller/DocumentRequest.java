package pl.com.bottega.documentmanagement.controller;

import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by paulina.pislewicz on 2016-07-03.
 */
public class DocumentRequest {
    private String title;
    private String content;
    private DocumentNumber documentNumber;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public DocumentNumber getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(DocumentNumber documentNumber) {
        this.documentNumber = documentNumber;
    }
}
