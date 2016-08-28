package pl.com.bottega.documentmanagement.infrastructure;

import pl.com.bottega.documentmanagement.domain.DocumentBuilder;

import java.util.Date;

/**
 * Created by anna on 28.08.2016.
 */
public class XMLDocumentBuilder implements DocumentBuilder {

    private StringBuilder xmlBuilder;

    @Override
    public void start() {
        xmlBuilder = new StringBuilder();
        xmlBuilder.append("<document>");
    }

    @Override
    public void addTitle(String title) {
        xmlBuilder.append("<title>" + title + "</title>");
    }

    @Override
    public void addContent(String content) {
        xmlBuilder.append("<content>" + content + "</content>");
    }

    @Override
    public void addCreatedAt(Date createdAt) {
        xmlBuilder.append("<createdAt>" + createdAt + "</createdAt");
    }

    @Override
    public void addStatus(String name) {
        xmlBuilder.append("<status>" + name + "</status>");
    }

    @Override
    public void end() {
        xmlBuilder.append("</document>");
    }

    public String xml() {
        return xmlBuilder.toString();
    }
}
