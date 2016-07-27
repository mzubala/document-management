package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.DocumentCriteria;
import pl.com.bottega.documentmanagement.api.DocumentDto;
import pl.com.bottega.documentmanagement.api.DocumentsCatalog;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by paulina.pislewicz on 2016-07-12.
 */
@Component
public class JPQLDocumentCatalog implements DocumentsCatalog {
    @PersistenceContext
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
    public Iterable<DocumentDto> find(DocumentCriteria documentCriteria) {
        checkNotNull(documentCriteria);
        String query = "SELECT new pl.com.bottega.documentmanagement.api.DocumentDto" +
                "(d.documentNumber.number,   d.title, d.content, d.documentStatus, d.createdAt, d.updatedAt, d.verificatedAt, " +
                "d.creator.employeeId.id, d.verificator.employeeId.id)" +
                " FROM Document d ";

        String condition_docNr = "WHERE d.documentNumber.number = :number";
        String what_title = "WHERE d.title = :title";
        String what_content = "WHERE d.content = :content";
        String condition_docStatus = "WHERE d.documentStatus = :status";
        String when_created = "WHERE d.createdAt BETWEEN :createdFrom AND :createdUntil";
        String when_updated = "WHERE d.updatedAt BETWEEN :updatedFrom AND :updatedUntil";
        String when_verificated = "WHERE d.verificatedAt BETWEEN :verificatedFrom AND :verificatedUntil";
        String who_created = "WHERE d.creator.employeeId.id =:creatorId ";
        String who_verified = "WHERE d.verificator.employeeId.id = :verificatorId";


        if (documentCriteria.isDocumentNumberDefined())
            return entityManager.createQuery(query + condition_docNr, DocumentDto.class).
                    setParameter("documentNumber", documentCriteria.getDocumentNumber().getNumber()).getResultList();

        if (documentCriteria.isQueryDefined())
            if (documentCriteria.isTitleDefined())
            return entityManager.createQuery(query + what_title, DocumentDto.class).
                    setParameter("title", "%" + documentCriteria.getTitle() +"%").getResultList();
            if (documentCriteria.isContentDefined())
                return entityManager.createQuery(query + what_content, DocumentDto.class).
                setParameter("content", "%" + documentCriteria.getContent() +"%").getResultList();

        if (documentCriteria.isStatusDefined())
            return entityManager.createQuery(query + condition_docStatus, DocumentDto.class).
                    setParameter("status", documentCriteria.getStatus()).getResultList();

        if (documentCriteria.isCreatesDatesDefined())
            if (documentCriteria.isCreatedFromDefined()) {
                return entityManager.createQuery(query + when_created, DocumentDto.class).
                        setParameter("createdFrom", documentCriteria.getCreatedFrom()).getResultList();
            } else
                return entityManager.createQuery(query + when_created, DocumentDto.class).
                        setParameter("createdUntil", documentCriteria.getCreatedUntil()).getResultList();

        if (documentCriteria.isUpdatedDatesDefined())
            if (documentCriteria.isUpdatedFromDefined()) {
                return entityManager.createQuery(query + when_updated, DocumentDto.class).
                        setParameter("updatedFrom", documentCriteria.getUpdatedFrom()).getResultList();
            } else
                return entityManager.createQuery(query + when_created, DocumentDto.class).
                        setParameter("updatedUntil", documentCriteria.getUpdatedUntil()).getResultList();

        if (documentCriteria.isVerifiesDatesDefined())
            if (documentCriteria.isVerifiedFromDefined()) {
                return entityManager.createQuery(query + when_verificated, DocumentDto.class).
                        setParameter("verificatedFrom", documentCriteria.getVerifiedFrom()).getResultList();
            } else
                return entityManager.createQuery(query + when_verificated, DocumentDto.class).
                        setParameter("verificatedUntil", documentCriteria.getVerifiedUntil()).getResultList();

        if (documentCriteria.isCreatedByDefined())
            return entityManager.createQuery(query + who_created, DocumentDto.class).
                    setParameter("creatorId", documentCriteria.getCreatedBy()).getResultList();

        if (documentCriteria.isVerifiedByDefined())
            return entityManager.createQuery(query + who_verified, DocumentDto.class).
                    setParameter("verificatorId", documentCriteria.getVerifiedBy()).getResultList();

        return entityManager.createQuery(query).getResultList();


    }

}