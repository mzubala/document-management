package pl.com.bottega.documentmanagement.application;

/**
 * Created by maciuch on 21.08.16.
 */
public class UpdateDocumentCommand extends SpringCommand {
    @Override
    public void execute() {
        System.out.println("Executing update document");
    }
}
