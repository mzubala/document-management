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

    public void setDocumentStatus(DocumentStatus documentStatus) {
        this.documentStatus = documentStatus;
    }

    public Long getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(Long verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(Date createdFrom) {
        this.createdFrom = createdFrom;
    }

    public Date getCreatedUntil() {
        return createdUntil;
    }

    public void setCreatedUntil(Date createdUntil) {
        this.createdUntil = createdUntil;
    }

    public Date getVerifiedFrom() {
        return verifiedFrom;
    }

    public void setVerifiedFrom(Date verifiedFrom) {
        this.verifiedFrom = verifiedFrom;
    }

    public Date getVerifiedUntil() {
        return verifiedUntil;
    }

    public void setVerifiedUntil(Date verifiedUntil) {
        this.verifiedUntil = verifiedUntil;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public boolean isStatusDefined() {
        return documentStatus != null;
    }

    public boolean isCreatedByDefined() {
        return createdBy != null;
    }

    public boolean isVerifiedByDefined() {
        return verifiedBy != null;
    }

    public boolean isCreatedDateDefined() {
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

    public boolean isVerifiedDateDefined() {
        return verifiedFrom != null || verifiedUntil != null;
    }

    public boolean isVerifiedFromDefined() {
        return verifiedFrom != null;
    }

    public boolean isVerifiedUntilDefined() {
        return verifiedUntil != null;
    }

    @Override
    public String toString() {
        return "DocumentCriteria{" +
                "documentStatus=" + documentStatus +
                ", verifiedBy=" + verifiedBy +
                ", createdBy=" + createdBy +
                ", createdFrom=" + createdFrom +
                ", createdUntil=" + createdUntil +
                ", verifiedFrom=" + verifiedFrom +
                ", verifiedUntil=" + verifiedUntil +
                ", query=" + query + "}";
    }
}
