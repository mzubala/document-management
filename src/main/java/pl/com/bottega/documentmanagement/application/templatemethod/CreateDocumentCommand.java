package pl.com.bottega.documentmanagement.application.templatemethod;

import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

import java.util.Scanner;

/**
 * Created by Dell on 2016-08-21.
 */
public class CreateDocumentCommand extends SpringCommand {

    @Override
    public void execute() {

        System.out.println("Title: ");
        String title = new Scanner(System.in).next();
        System.out.println("Content: ");
        String content = new Scanner(System.in).next();
        System.out.println("Creating document " + title);
        DocumentFlowProcess documentFlowProcess = getBean(DocumentFlowProcess.class);
        DocumentNumber nr = documentFlowProcess.create(title, content);
        System.out.println("Created document: " + nr.getNumber());
    }
}
