package pl.com.bottega.documentmanagement.infrastructure;

import pl.com.bottega.documentmanagement.domain.DocumentBuilder;

import java.util.Date;

/**
 * Created by maciuch on 28.08.16.
 */
public class ConsoleDocumentPrinter implements DocumentBuilder {

    @Override
    public void start() {
    }

    @Override
    public void addTitle(String title) {
        System.out.println("Title: " + title);
    }

    @Override
    public void addContent(String content) {
        System.out.println("Content: " + content);
    }

    @Override
    public void addCreatedAt(Date createdAt) {
        System.out.println("Created at: " + createdAt);
    }

    @Override
    public void addStatus(String name) {
        System.out.println("Status: " + name);
    }

    @Override
    public void end() {

    }
}
