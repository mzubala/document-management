package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentStatus;

import java.util.Date;

/**
 * Created by bartosz.paszkowski on 12.06.2016.
 */
public class DocumentDto {

    private String number;
    private String title;
    private String content;
    private String documentStatus;
    private Long creatorId, verifierId;
    private Date createdAt, verificationAt, updatedAt;
    private Boolean deleted;




    public DocumentDto(String number, String title, String content, DocumentStatus documentStatus,
                       Date createAt, Date verificationAt, Date updateAt, Long creatorId, Long verifierId, Boolean deleted) {
        this.number = number;
        this.title = title;
        this.content = content;
        this.documentStatus = documentStatus.name();
        this.createdAt = createAt;
        this.verificationAt = verificationAt;
        this.updatedAt = updateAt;
        this.creatorId = creatorId;
        this.verifierId = verifierId;
        this.deleted = deleted;
    }
    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    public Long getCreatorId() {
        return creatorId;
    }

    public Long getVerifierId() {
        return verifierId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getVerificationAt() {
        return verificationAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
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
        return documentStatus;
    }

    public void setStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }

}
