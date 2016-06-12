package pl.com.bottega.documentmanagement.domain;

import pl.com.bottega.documentmanagement.infrastructure.DocumentDto;

/**
 * Created by paulina.pislewicz on 2016-06-12.
 */
public class Document {
    public Document(DocumentNumber documentNumber, String title, String content) {

    }
    public void change (String title, String content){

    }
    public void verify(Employee employee){

    }
    public DocumentDto export(){
        DocumentDto dto = new DocumentDto();
        return dto;
    }

    public void confirm(Employee confirmator) {

    }

    public void confirm(Employee confirmator, Employee forEmployee) {
    }
}
