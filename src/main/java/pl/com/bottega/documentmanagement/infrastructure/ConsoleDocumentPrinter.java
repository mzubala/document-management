package pl.com.bottega.documentmanagement.infrastructure;

import pl.com.bottega.documentmanagement.domain.DocumentBuilder;

import java.util.Date;

/**
 * Created by Dell on 2016-08-28.
 */
public class ConsoleDocumentPrinter implements DocumentBuilder {

    @Override
    public void start() {
    }

    @Override
    public void addTitile(String title) {
        System.out.println("Title: " + title);
    }

    @Override
    public void addContent(String content) {
        System.out.println("Content: " + content);
    }

    @Override
    public void createdAt(Date createAt) {
        System.out.println("Created at: " + createAt);
    }

    @Override
    public void addStatus(String status) {
        System.out.println("Status: " + status);
    }

    @Override
    public void end() {
    }
}
