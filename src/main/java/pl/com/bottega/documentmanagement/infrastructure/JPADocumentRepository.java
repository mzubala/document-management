package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Repository;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by bartosz.paszkowski on 29.06.2016.
 */
@Repository
public class JPADocumentRepository implements DocumentRepository{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Document document) {
        entityManager.merge(document);
    }

    @Override
    public Document load(DocumentNumber documentNumber) {
        return entityManager.createQuery("SELECT d " +
                "from Document d " +
                "where documentNumber =:documentNumber", Document.class).
                setParameter("documentNumber",documentNumber).getSingleResult();
    }
}
