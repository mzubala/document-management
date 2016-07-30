package pl.com.bottega.documentmanagement.api;

/**
 * Created by maciuch on 30.07.16.
 */
public class DocumentSearchResults {

    private Iterable<DocumentDto> documents;

    private Long perPage, pageNumber, totalPages;

    public DocumentSearchResults(Iterable<DocumentDto> documents, Long perPage, Long pageNumber, Long totalResults) {
        this.documents = documents;
        this.perPage = perPage;
        this.pageNumber = pageNumber;
        this.totalPages = totalResults / perPage + (totalResults % perPage > 0 ? 1 : 0);
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

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }
}
