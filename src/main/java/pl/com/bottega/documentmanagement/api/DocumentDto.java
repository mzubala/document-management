package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentStatus;

import java.util.Date;

/**
 * Created by maciuch on 12.06.16.
 */
public class DocumentDto {

    private String number;
    private String title;
    private String content;
    private String documentStatus;
    private Long creatorId, verificatorId;
    private Date createdAt, verifiedAt, updatedAt;

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Long getVerificatorId() {
        return verificatorId;
    }

    public void setVerificatorId(Long verificatorId) {
        this.verificatorId = verificatorId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Date verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public DocumentDto(String number, String title, String content, DocumentStatus documentStatus, Date createdAt, Date verifiedAt, Date updatedAt, Long creatorId, Long verificatorId) {
        this.number = number;
        this.title = title;
        this.content = content;
        this.documentStatus = documentStatus.name();
        this.createdAt = createdAt;
        this.verifiedAt = verifiedAt;
        this.updatedAt = updatedAt;
        this.creatorId = creatorId;
        this.verificatorId = verificatorId;
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

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String status) {
        this.documentStatus = status;
    }
}
