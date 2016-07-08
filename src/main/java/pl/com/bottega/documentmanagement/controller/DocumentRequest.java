package pl.com.bottega.documentmanagement.controller;

/**
 * Created by bartosz.paszkowski on 03.07.2016.
 */
public class DocumentRequest {

    private String title;
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
