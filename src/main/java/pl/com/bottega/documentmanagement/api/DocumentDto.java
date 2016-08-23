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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DocumentDto that = (DocumentDto) o;

        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (documentStatus != null ? !documentStatus.equals(that.documentStatus) : that.documentStatus != null)
            return false;
        if (creatorId != null ? !creatorId.equals(that.creatorId) : that.creatorId != null) return false;
        if (verifierId != null ? !verifierId.equals(that.verifierId) : that.verifierId != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (verificationAt != null ? !verificationAt.equals(that.verificationAt) : that.verificationAt != null)
            return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;
        return deleted != null ? deleted.equals(that.deleted) : that.deleted == null;

    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (documentStatus != null ? documentStatus.hashCode() : 0);
        result = 31 * result + (creatorId != null ? creatorId.hashCode() : 0);
        result = 31 * result + (verifierId != null ? verifierId.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (verificationAt != null ? verificationAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        result = 31 * result + (deleted != null ? deleted.hashCode() : 0);
        return result;
    }
}
