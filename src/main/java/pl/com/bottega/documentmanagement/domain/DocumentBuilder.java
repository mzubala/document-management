package pl.com.bottega.documentmanagement.domain;

import java.util.Date;

/**
 * Created by anna on 28.08.2016.
 */
public interface DocumentBuilder {

    void start();

    void addTitle(String title);

    void addContent(String content);

    void addCreatedAt(Date createdAt);

    void addStatus(String name);

    void end();
}
