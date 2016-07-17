package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentStatus;

import java.util.Date;

/**
 * Created by maciuch on 12.06.16.
 */
public class DocumentCriteria {

    private DocumentStatus documentStatus;
    private Long verifiedBy;
    private Long createdBy;
    private Date createdFrom, createdUntil;
    private Date verifiedFrom, verifiedUntil;
    private String query;

    public DocumentStatus getDocumentStatus() {
        return documentStatus;
    }

    public Long getVerifiedBy() {
        return verifiedBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public Date getCreatedFrom() {
        return createdFrom;
    }

    public Date getCreatedUntil() {
        return createdUntil;
    }

    public Date getVerifiedFrom() {
        return verifiedFrom;
    }

    public Date getVerifiedUntil() {
        return verifiedUntil;
    }

    public String getQuery() {
        return query;
    }

    public void setDocumentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
    }

    public void setVerifiedBy(Long verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedFrom(Date createdFrom) {
        this.createdFrom = createdFrom;
    }

    public void setCreatedUntil(Date createdUntil) {
        this.createdUntil = createdUntil;
    }

    public void setVerifiedFrom(Date verifiedFrom) {
        this.verifiedFrom = verifiedFrom;
    }

    public void setVerifiedUntil(Date verifiedUntil) {
        this.verifiedUntil = verifiedUntil;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isStatusDefinied() {
        return documentStatus != null;
    }

    public boolean isCreatedByDefined() {
        return createdBy != null;
    }

    public boolean isCreatedDatesDefined() {
        return createdFrom != null || createdUntil != null;
    }

    public boolean isCreatedFromDefined() {
        return createdFrom != null;
    }

    public boolean isCreatedUntilDefined() {
        return createdUntil != null;
    }

    public boolean isQueryDefined() {
        return query != null;
    }

    public boolean isVerifiedByDefined() {
        return verifiedBy != null;
    }

    public boolean isVerifiedDatesDefined() {
        return verifiedFrom != null || verifiedUntil != null;
    }

    public boolean isVerfiedFromDefined() {
        return verifiedFrom != null;
    }

    public boolean isVerifiedUntilDefined() {
        return verifiedUntil != null;
    }
}
