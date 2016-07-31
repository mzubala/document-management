package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.DocumentStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;


/**
 * Created by Wojciech Winiarski on 12.06.16.
 */
public class DocumentCriteria {

    private static final Long DEFAULT_PER_PAGE = 2L;
    private static final Long DEFAULT_PAGE_NUMBER = 1L;

    private DocumentStatus status;
    private Long verifiedBy;
    private Long createdBy;
    private Date createdFrom, createdUntil;
    private Date verifiedFrom, verifiedUntil;
    private String query;
    private Long pageNumber = DEFAULT_PER_PAGE;
    private Long perPage = DEFAULT_PAGE_NUMBER;


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

    public void setCreatedFrom(Date createdForm) {
        this.createdFrom = createdForm;
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

    public boolean isVerifyByDefined() {
        return verifiedBy != null;
    }

    public boolean isVerifiedUntilDefined() {
        return verifiedUntil != null;
    }

    public boolean isVerifyFromDefined() {
       return verifiedFrom != null;
    }

    public boolean isVerifyDateDefined() {
        return verifiedFrom != null ||
                verifiedUntil != null;
    }
    public Long getPageNumber(){
        return pageNumber;
    }
    public void setPageNumber(Long pageNumber){
        this.pageNumber = pageNumber;
    }
    public Long getPerPage(){
        return perPage;
    }
    public void setPerPage(Long perPage){
        this.perPage = perPage;
    }
}
