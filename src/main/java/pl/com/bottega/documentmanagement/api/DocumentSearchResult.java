package pl.com.bottega.documentmanagement.api;

/**
 * Created by anna on 30.07.2016.
 */
public class DocumentSearchResult {

    private Iterable<DocumentDto> documents;

    private Long perPage;
    private Long pageNumber;
    private Long totalPages;

    public DocumentSearchResult(Iterable<DocumentDto> documents, Long perPage, Long pageNumber, Long totalResults) {
        this.documents = documents;
        this.perPage = perPage;
        this.pageNumber = pageNumber;
        this.totalPages = totalResults / perPage + (totalResults % perPage > 0 ? 1 : 0);
    }


    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    public Long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getPerPage() {
        return perPage;
    }

    public void setPerPage(Long perPage) {
        this.perPage = perPage;
    }

    public Iterable<DocumentDto> getDocuments() {
        return documents;
    }

    public void setDocuments(Iterable<DocumentDto> documents) {
        this.documents = documents;
    }
}
