package pl.com.bottega.documentmanagement.application;

import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

import java.util.Scanner;

/**
 * Created by bartosz.paszkowski on 21.08.2016.
 */
public class CreateDocumentCommand extends SpringCommand {
    @Override
    public void execute() {

        System.out.println("Title: ");
        String title = new Scanner(System.in).nextLine();
        System.out.println("Content: ");
        String content = new Scanner(System.in).nextLine();
        System.out.println("Creating document " + title);
        DocumentFlowProcess documentFlowProcess = getBean(DocumentFlowProcess.class);
        DocumentNumber number = documentFlowProcess.create(title,content);
        System.out.println("Created document: " + number);

    }
}
