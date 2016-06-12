package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentNumber;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Wojciech Winiarski on 12.06.2016.
 */
public class DocumentFlowProcess {

    public DocumentNumber create(String title, String content){

        checkNotNull(title);
        checkNotNull(content);



        return null;
    }

    public void change(DocumentNumber documentNumber, String newTitle, String newContent){
        checkNotNull(documentNumber);
        checkNotNull(newContent);
        checkNotNull(newTitle);

    }

    public void verify(DocumentNumber documentNumber){

        checkNotNull(documentNumber);
    }
    public void publish(DocumentNumber documentNumber){
        checkNotNull(documentNumber);

    }
    public void archive(DocumentNumber  documentNumber){

        checkNotNull(documentNumber);
    }

    public DocumentNumber CreateNewVersion(DocumentNumber documentNumber){
        checkNotNull(documentNumber);

        return null;
    }

}
