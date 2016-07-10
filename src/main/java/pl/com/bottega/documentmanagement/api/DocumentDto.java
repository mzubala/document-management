package pl.com.bottega.documentmanagement.api;

import java.util.Date;

/**
 * Created by maciuch on 12.06.16.
 */
public class DocumentDto {

    private String number;
    private String title;
    private String content;
    private String status;
    private Long creatorId, verificatorId;
    private Date createdAt, verificatedAt, updatedAt;

    public DocumentDto(String number, String title, String content, String status, Date createdAt, Date verificatedAt, Date updatedAt) {
        this.number = number;
        this.title = title;
        this.content = content;
        this.status = status;
        this.createdAt = createdAt;
        this.verificatedAt = verificatedAt;
        this.updatedAt = updatedAt;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
