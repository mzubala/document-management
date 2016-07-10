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
    private String status;
    private Long creatorId, verifierId;
    private Date createAt, verificationAt,updateAt;


    public DocumentDto(String number, String title, String content, DocumentStatus status,
                       Date createAt, Date verificationAt, Date updateAt, Long creatorId,Long verifierId) {
        this.number = number;
        this.title = title;
        this.content = content;
        this.status = status.name();
        this.createAt = createAt;
        this.verificationAt = verificationAt;
        this.updateAt = updateAt;
        this.creatorId = creatorId;
        this.verifierId = verifierId;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public Long getVerifierId() {
        return verifierId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public Date getVerificationAt() {
        return verificationAt;
    }

    public Date getUpdateAt() {
        return updateAt;
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
