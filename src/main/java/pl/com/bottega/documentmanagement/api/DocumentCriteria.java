package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;


/**
 * Created by Wojciech Winiarski on 12.06.16.
 */
public class DocumentCriteria {

    private DocumentStatus status;
    private Long verifiedBy;
    private Long createdBy;
    private Date createdForm, createdUntil;
    private Date verifiedFrom, verifiedUntil;
    private String query;





    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
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
        return createdForm;
    }

    public void setCreatedFrom(Date createdForm) {
        this.createdForm = createdForm;
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

    public boolean isStatusDefine() {
        return status != null;
    }

    public boolean isCreatedByDefined() {
        return createdBy != null;
    }


    public boolean isCreatedDetesDefined() {
        return createdForm != null || createdUntil != null;
    }

    public boolean isCreatedFromDefined() {
        return createdForm != null;
    }

    public boolean isCreatedUntilDefined() {
        return createdUntil != null;

    }

    public boolean isQueryDefined() {
        return query != null;

    }
}
