package pl.com.bottega.documentmanagement.application.templatemethod;

import org.springframework.context.ApplicationContext;

/**
 * Created by Dell on 2016-08-21.
 */
public class DocumentManagementCommandFactory implements CommandFactory {


    private final ApplicationContext applicationContext;

    public DocumentManagementCommandFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Command createCommand(String command) {
        SpringCommand springCommand;
        if (command.equals("1"))
            springCommand = new CreateDocumentCommand();
        else if (command.equals("2"))
            springCommand = new SearchDocumentCommand();
        else if (command.equals("3"))
            springCommand = new UpdateDocumentCommand();
        else if (command.equals("4"))
            springCommand = new VerifyDocumentCommand();
        else
            return new UnknownCommand();
        springCommand.setApplicationContext(applicationContext);
        return springCommand;
    }
}
