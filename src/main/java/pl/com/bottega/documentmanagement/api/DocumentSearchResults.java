package pl.com.bottega.documentmanagement.api;

/**
 * Created by paulina.pislewicz on 2016-07-30.
 */
public class DocumentSearchResults {
    private Iterable <DocumentDto> documents;
    private Long perPage;
    private Long pageNumber;

    public DocumentSearchResults(Iterable<DocumentDto> documents, Long totalResults, Long pageNumber, Long perPage) {
        this.documents = documents;
        this.totalPages = totalResults/perPage + (totalResults % perPage>0? 1 : 0);
        this.pageNumber = pageNumber;
        this.perPage = perPage;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Iterable<DocumentDto> getDocuments() {
        return documents;
    }

    public void setDocuments(Iterable<DocumentDto> documents) {
        this.documents = documents;
    }

    public Long getPerPage() {
        return perPage;
    }

    public void setPerPage(Long perPage) {
        this.perPage = perPage;
    }

    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    private Long totalPages;
}
