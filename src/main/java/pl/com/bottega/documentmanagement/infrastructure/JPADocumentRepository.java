package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Repository;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by maciuch on 22.06.16.
 */
@Repository
public class JPADocumentRepository implements DocumentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Document document) {
        entityManager.merge(document);
    }

    @Override
    public Document load(DocumentNumber documentNumber) {
        return entityManager.createQuery("FROM Document d WHERE d.documentNumber = :num", Document.class)
                .setParameter("num", documentNumber)
                .getSingleResult();
    }
}
