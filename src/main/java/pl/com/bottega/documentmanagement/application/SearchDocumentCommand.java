package pl.com.bottega.documentmanagement.application;

/**
 * Created by maciuch on 21.08.16.
 */
public class SearchDocumentCommand extends SpringCommand {
    @Override
    public void execute() {
        System.out.println("Executing search document");
    }
}
