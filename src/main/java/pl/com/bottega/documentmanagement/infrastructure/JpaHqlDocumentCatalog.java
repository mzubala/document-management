package pl.com.bottega.documentmanagement.infrastructure;

import org.hibernate.sql.Select;
import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.DocumentCriteria;
import pl.com.bottega.documentmanagement.api.DocumentsCatalog;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by paulina.pislewicz on 2016-07-12.
 */
public class JpaHqlDocumentCatalog implements DocumentsCatalog {
    EntityManager entityManager;

    public DocumentDto get(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        return entityManager.createQuery("SELECT new pl.com.bottega.documentmanagement.api.DocumentDto" +
                "(d.documentNumber, d.title, d.content, d.status, d.createdAt, d.updatedAt, d.verificatedAt, d.createorId, d.verificatorId)" +
                " FROM DocumentDto d WHERE d.documentNumber = :documentNumber", DocumentDto.class).
                setParameter("documentNumber", documentNumber).getSingleResult();
    }

    @Override
    public List<DocumentDto> find(DocumentCriteria documentCriteria) {
        return entityManager.createQuery("SELECT new pl.com.bottega.documentmanagement.api.DocumentDto" +
                        "(d.documentNumber, d.title, d.content, d.status, d.createdAt, d.updatedAt, d.verificatedAt, d.createorId, d.verificatorId)" +
                        " FROM DocumentDto d " +
                        "WHERE d.documentNumber = :documentNumber AND d.title = :title AND d.content = :content AND d.status = :status AND d.createdAt =: createdAt AND d.upatedAt = :updatedAt AND d.verificatedAt =: verificatedAt AND d.creatorId =:creatorId AND d.verificatorId =: verificatorId",
                DocumentDto.class).
                setParameter("documentNumber", documentCriteria.getDocumentNumber().getNumber()).
                setParameter("title", documentCriteria.getTitle()).
                setParameter("content", documentCriteria.getStatus()).
                setParameter("status", documentCriteria.getStatus()).
                setParameter("createdAt", documentCriteria.isCreatedFromDefined()).
                getResultList();
    }
}