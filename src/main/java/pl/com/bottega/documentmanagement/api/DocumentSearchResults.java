package pl.com.bottega.documentmanagement.api;

/**
 * Created by bartosz.paszkowski on 30.07.2016.
 */
public class DocumentSearchResults {

    private Iterable<DocumentDto> documents;
    private int perPage, pageNumber;
    private Long totalPages;

    public DocumentSearchResults(Iterable<DocumentDto> documents, int perPage, int pageNumber, Long totalResults) {
        this.documents = documents;
        this.perPage = perPage;
        this.pageNumber = pageNumber;
        this.totalPages = (totalResults/perPage) + (totalResults % perPage > 0 ? 1:0);
    }

    public Iterable<DocumentDto> getDocuments() {
        return documents;
    }

    public void setDocuments(Iterable<DocumentDto> documents) {
        this.documents = documents;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }
}
