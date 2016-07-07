package pl.com.bottega.documentmanagement.infrastructure;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Admin on 02.07.2016.
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
        String hqlQuery = "from Document where documentNumber =:number";
        return entityManager.createQuery(hqlQuery, Document.class)
                .setParameter("number", documentNumber)
                .getSingleResult();
    }
}
