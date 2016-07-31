package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentStatus;

import java.util.Date;

/**
 * Created by maciuch on 12.06.16.
 */
public class DocumentCriteria {

    private static final Long DEFAULT_PER_PAGE = 2l;
    private static final Long DEFAULT_PAGE_NUMBER = 1l;

    private DocumentStatus status;
    private Long verifiedBy;
    private Long createdBy;
    private Date createdFrom, createdUntil;
    private Date verifiedFrom, verifiedUntil;
    private String query;
    private Long perPage = DEFAULT_PER_PAGE;
    private Long pageNumber = DEFAULT_PAGE_NUMBER;

    public void setPerPage(Long perPage) {
        this.perPage = perPage;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    private Long perPage = DEFAULT_PER_PAGE;
    private Long pageNumber = DEFAULT_PAGE_NUMBER;

    public Long getPerPage() {
        return perPage;
    }

    public void setPerPage(Long perPage) {
        this.perPage = perPage;
    }

    public Long getPageNr() {
        return pageNumber;
    }

    public void setPageNr(Long pageNr) {
        this.pageNumber = pageNr;
    }

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
        return status != null;
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

    public boolean isVerifiedUntilDefined() {
        return verifiedUntil != null;
    }

    public boolean isVerifiedFromDefined() {
        return verifiedFrom != null;
    }

    public Long getPageNumber() {
        return pageNumber;
    }

    public Long getPerPage() {
        return perPage;
    }
}
