package pl.com.bottega.documentmanagement.api;

import com.google.common.collect.Iterables;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentCriteria;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;
import pl.com.bottega.documentmanagement.infrastructure.DocumentDto;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by anna on 12.06.2016.
 */
public class DocumentsCatalog {

    private DocumentRepository documentRepository;

    public DocumentDto get(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        Document document = documentRepository.load(documentNumber);

        return document.export();
    }

    public Iterable<DocumentDto> find(DocumentCriteria documentCriteria) {
        checkNotNull(documentCriteria);
        Iterable<Document> documents = documentRepository.find(documentCriteria);

        //równoważne zapisy:
        /*Collection<DocumentDto> returnValue = new ArrayList<>();
        for(Document document : documents)
            returnValue.add(document.export());
        return returnValue;*/

        //return Iterables.transform(documents, (document) -> document.export());

        //poniżej jeszcze lepsza wersja
        return Iterables.transform(documents, Document::export);
    }
}
