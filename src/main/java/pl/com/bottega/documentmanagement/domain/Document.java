package pl.com.bottega.documentmanagement.domain;

import pl.com.bottega.documentmanagement.infrastructure.DocumentDto;

/**
 * Created by maciuch on 12.06.16.
 */
public class Document {

    public Document(DocumentNumber documentNumber, String content, String title) {

    }

    public void change(String title, String content) {

    }

    public void verify(Employee employee) {

    }

    public void confirm(Employee conirmator) {

    }

    public void confirm(Employee confirmator, Employee forEmployee) {

    }

    public DocumentDto export() {
        DocumentDto dto = new DocumentDto();
        return dto;
    }

}
