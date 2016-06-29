package pl.com.bottega.documentmanagement.api;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by maciuch on 12.06.16.
 */
@Service
public class DocumentsCatalog {

    private DocumentRepository documentRepository;
    private DocumentDto documentDto;

    public DocumentsCatalog(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
        this.documentDto = new DocumentDto();
    }

    @Transactional
    public DocumentDto get(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        Document document = documentRepository.load(documentNumber);
        documentDto.setNumber(document.getNumber().toString());
        documentDto.setTitle(document.getTitle());
        documentDto.setContent(document.getContent());
        return documentDto;
    }

    public Iterable<DocumentDto> find(DocumentCriteria documentCriteria) {
        checkNotNull(documentCriteria);

        return null;
    }

}
