package pl.com.bottega.documentmanagement.controller;

/**
 * Created by maciuch on 03.07.16.
 */
public class DocumentRequest {

    private String title;
    private String content;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
