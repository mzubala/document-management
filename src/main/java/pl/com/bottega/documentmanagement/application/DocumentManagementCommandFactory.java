package pl.com.bottega.documentmanagement.application;


import org.springframework.context.ApplicationContext;

/**
 * Created by anna on 21.08.2016.
 */
public class DocumentManagementCommandFactory implements CommandFactory {

    private ApplicationContext applicationContext;

    public DocumentManagementCommandFactory(ApplicationContext applicationContext){
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
        else springCommand = new UnknownCommand();
        springCommand.setApplicationContext(applicationContext);
        return springCommand;

    }
}
