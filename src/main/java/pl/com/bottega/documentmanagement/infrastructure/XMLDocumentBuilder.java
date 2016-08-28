package pl.com.bottega.documentmanagement.infrastructure;

import pl.com.bottega.documentmanagement.domain.DocumentBuilder;

import java.util.Date;

/**
 * Created by Dell on 2016-08-28.
 */
public class XMLDocumentBuilder implements DocumentBuilder {

    private StringBuilder xmlBuilder;

    @Override
    public void start() {
        xmlBuilder = new StringBuilder();
        xmlBuilder.append("<document>");
    }

    @Override
    public void addTitile(String title) {
        xmlBuilder.append("<title>" + title + "</title>");
    }

    @Override
    public void addContent(String content) {
        xmlBuilder.append("<content>" + content + "</content>");
    }

    @Override
    public void createdAt(Date createAt) {
        xmlBuilder.append("<createdAt>" + createAt + "</createdAt>");
    }

    @Override
    public void addStatus(String status) {
        xmlBuilder.append("<status>" + status + "</status>");
    }

    @Override
    public void end() {
        xmlBuilder.append("<document>");
    }

    public String xml() {
        return xmlBuilder.toString();
    }
}
