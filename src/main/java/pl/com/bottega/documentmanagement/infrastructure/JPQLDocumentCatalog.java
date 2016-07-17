package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.DocumentCriteria;
import pl.com.bottega.documentmanagement.api.DocumentDto;
import pl.com.bottega.documentmanagement.api.DocumentsCatalog;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by paulina.pislewicz on 2016-07-12.
 */
//@Component
public class JPQLDocumentCatalog implements DocumentsCatalog {
    //@PersistenceContext
    EntityManager entityManager;

    public DocumentDto get(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        String jpql = "SELECT new pl.com.bottega.documentmanagement.api.DocumentDto" +
                "(d.documentNumber.number,   d.title, d.content, d.documentStatus, d.createdAt, d.updatedAt, d.verificatedAt, d.creator.employeeId.id, d.verificator.employeeId.id)" +
                " FROM Document d WHERE d.documentNumber = :documentNumber";

        return entityManager.createQuery(jpql, DocumentDto.class).
                setParameter("documentNumber", documentNumber.getNumber()).getSingleResult();
    }


    @Override
    public Iterable <DocumentDto> find(DocumentCriteria documentCriteria) {
        checkNotNull(documentCriteria);
        return entityManager.createQuery("SELECT new pl.com.bottega.documentmanagement.api.DocumentDto" +
                        "(d.documentNumber.number,   d.title, d.content, d.documentStatus, d.createdAt, d.updatedAt, d.verificatedAt, d.creator.employeeId.id, d.verificator.employeeId.id)" +
                        " FROM Document d " +
                        "WHERE d.documentNumber.number = :documentNumber AND d.title = :title AND d.content = :content AND d.documentStatus = :status AND d.createdAt BETWEEN :createdFrom AND :createdUntil AND d.updatedAt BETWEEN :updatedFrom AND :updatedUntil AND d.verificatedAt BETWEEN :verificatedFrom AND :verificatedUntil AND d.creator.employeeId.id =:creatorId AND d.verificator.employeeId.id = :verificatorId ",
                DocumentDto.class).
                setParameter("documentNumber", documentCriteria.getDocumentNumber().getNumber()).
                setParameter("title", documentCriteria.getTitle()).
                setParameter("content", documentCriteria.getContent()).
                setParameter("status", documentCriteria.getStatus()).
                setParameter("createdFrom", documentCriteria.getCreatedFrom()).
                setParameter("createdUntil", documentCriteria.getCreatedUntil()).
                setParameter("updatedFrom", documentCriteria.getUpdatedFrom()).
                setParameter("updatedUntil", documentCriteria.getUpdatedUntil()).
                setParameter("verificatedFrom", documentCriteria.getVerifiedFrom()).
                setParameter("verificatedUntil", documentCriteria.getVerifiedUntil()).
                setParameter("creatorId", documentCriteria.getCreatedBy()).
                setParameter("verificatorId", documentCriteria.getVerifiedBy()).
                getResultList();
    }
}