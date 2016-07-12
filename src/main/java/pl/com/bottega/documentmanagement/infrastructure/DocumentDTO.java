package pl.com.bottega.documentmanagement.infrastructure;

import pl.com.bottega.documentmanagement.domain.DocumentStatus;

import java.util.Date;

/**
 * Created by paulina.pislewicz on 2016-06-12.
 */
public class DocumentDto {

    private String number;
    private String title;
    private String content;
    private String status;
    private Long creatorId, verificatorId;
    private Date createdAt, verificatedAt, updatedAt;

   public DocumentDto(String number, String title, String content, DocumentStatus status, Date createdAt, Date updatedAt, Date verificatedAt, Long creatorId, Long verificatorId) {
        this.number = number;
        this.title = title;
        this.content = content;
        this.status = status.name();
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.verificatedAt = verificatedAt;
        this.creatorId = creatorId;
        this.verificatorId = verificatorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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


    public Long getCreatorId() {
        return creatorId;
    }

    public Long getVerificatorId() {
        return verificatorId;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getVerificatedAt() {
        return verificatedAt;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }




}
