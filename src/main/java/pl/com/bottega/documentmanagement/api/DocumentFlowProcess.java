package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.UserId;

/**
 * Created by Dell on 2016-06-12.
 */
public class DocumentFlowProcess {

    public DocumentNumber create(String title, String content) {
        return null;
    }

    public void change(DocumentNumber documentNumber, String newTitle, String newContent) {

    }

    public void verify(DocumentNumber documentNumber) {

    }

    public void publish(DocumentNumber documentNumber) {

    }

    public void archive(DocumentNumber documentNumber) {

    }

    public DocumentNumber createNewVersion(DocumentNumber documentNumber) {
        return null;
    }
}
