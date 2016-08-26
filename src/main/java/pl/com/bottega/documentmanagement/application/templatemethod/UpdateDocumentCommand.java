package pl.com.bottega.documentmanagement.application.templatemethod;

import pl.com.bottega.documentmanagement.application.templatemethod.SpringCommand;

/**
 * Created by Dell on 2016-08-21.
 */
public class UpdateDocumentCommand extends SpringCommand {
    @Override
    public void execute() {
        System.out.println("Executing updating document");
    }
}
