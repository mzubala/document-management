package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentNumber;

/**
 * Created by paulina.pislewicz on 2016-06-12.
 */
public class DocumentFlowProcess {
    public DocumentNumber create(String title, String content){
        return null;
    }
    public void change(DocumentNumber documentNumber, String title, String content){

    }
    public void verify (DocumentNumber documentNumber){

    }

    public void publish (DocumentNumber documentNumber){

    }

    public void archive (DocumentNumber documentNumber){

    }
    public DocumentNumber createNewVersion (DocumentNumber documentNumber){
        return null;
    }
}
