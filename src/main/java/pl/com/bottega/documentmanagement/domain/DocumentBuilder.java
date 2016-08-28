package pl.com.bottega.documentmanagement.domain;

import java.util.Date;

/**
 * Created by Dell on 2016-08-28.
 */
public interface DocumentBuilder {


    void start();

    void addTitile(String title);

    void addContent(String content);

    void createdAt(Date createAt);

    void addStatus(String status);

    void end();
}
