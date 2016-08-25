package pl.com.bottega.documentmanagement.application;

/**
 * Created by Dell on 2016-08-21.
 */
public class SearchDocumentCommand extends SpringCommand {
    @Override
    public void execute() {
        System.out.println("Executing searching document");
    }
}
