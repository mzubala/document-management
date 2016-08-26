package pl.com.bottega.documentmanagement.application.templatemethod;

/**
 * Created by Dell on 2016-08-21.
 */
public class SearchDocumentCommand extends SpringCommand {
    @Override
    public void execute() {
        System.out.println("Executing searching document");
    }
}
