package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.DocumentCriteria;
import pl.com.bottega.documentmanagement.api.DocumentDto;
import pl.com.bottega.documentmanagement.api.DocumentSearchResults;
import pl.com.bottega.documentmanagement.api.DocumentsCatalog;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by paulina.pislewicz on 2016-07-12.
 */
//@Component
public class JPQLDocumentCatalog implements DocumentsCatalog {
   // @PersistenceContext
    EntityManager entityManager;

    @Override
    public DocumentDto get(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        String jpql = "select new pl.com.bottega.documentmanagement.api.DocumentDto(" +
                "d.documentNumber.number," +
                "d.title, d.content," +
                "d.status, d.createdAt, d.verifiedAt, d.updatedAt," +
                "d.creator.employeeId.id, d.verificator.employeeId.id) " +
                "from Document d where d.documentNumber=:documentNumber " +
                "AND d.deleted = false";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("documentNumber", documentNumber);
        return (DocumentDto) query.getSingleResult();
    }

    @Override
    public DocumentSearchResults find(DocumentCriteria documentCriteria) {
        checkNotNull(documentCriteria);
        String jpql = buildQuery(documentCriteria);
        Query query = entityManager.createQuery(jpql);
        fillStatement(documentCriteria, query);
        return new DocumentSearchResults(query.getResultList(),
                documentCriteria.getPerPage(),
                documentCriteria.getPageNumber(),
                0l);
    }

    private String buildQuery(DocumentCriteria documentCriteria) {
        List<String> queryList = new ArrayList<>();
        if (documentCriteria.isStatusDefined()) {
            queryList.add(" d.status =:status ");
        }
        if (documentCriteria.isCreatedByDefined()) {
            queryList.add(" d.creator.employeeId.id =:creatorId ");
        }
        if (documentCriteria.isVerifiedByDefined()) {
            queryList.add(" d.verificator.employeeId.id =:verificatorId ");
        }


        if (documentCriteria.isCreatedUntilDefined()) {
            queryList.add(" d.createdAt < :createUntil ");
        }
        if (documentCriteria.isCreatedFromDefined()) {
            queryList.add(" d.createdAt > :createFrom ");
        }

        if (documentCriteria.isVerifiedUntilDefined()) {
            queryList.add(" d.verifiedAt < :verifiedUntil ");
        }
        if (documentCriteria.isVerifiedFromDefined()) {
            queryList.add(" d.verifiedAt > :verifiedFrom ");
        }

        if (documentCriteria.isQueryDefined()) {
            queryList.add(" (d.title LIKE :query OR d.content LIKE :query) ");
        }

        String jpql = "SELECT new pl.com.bottega.documentmanagement.api.DocumentDto(" +
                "d.documentNumber.number," +
                "d.title, d.content," +
                "d.status, d.createdAt, d.verifiedAt, d.updatedAt," +
                "d.creator.employeeId.id, d.verificator.employeeId.id) " +
                "FROM Document d WHERE d.deleted = false ";

        if (queryList.size() > 0) {
            jpql += "AND";
            int limit = queryList.size();
            for (int index = 0; index < limit; index++) {
                jpql += queryList.get(index);
                if (index < limit - 1)
                    jpql += "AND";
            }
        }
        return jpql;
    }

    private void fillStatement(DocumentCriteria documentCriteria, Query query) {
        if (documentCriteria.isStatusDefined()) {
            query.setParameter("status", documentCriteria.getStatus());
        }
        if (documentCriteria.isCreatedByDefined()) {
            query.setParameter("creatorId", documentCriteria.getCreatedBy());
        }
        if (documentCriteria.isVerifiedByDefined()) {
            query.setParameter("verificatorId", documentCriteria.getVerifiedBy());
        }
        if (documentCriteria.isCreatedUntilDefined()) {
            query.setParameter("createUntil", documentCriteria.getCreatedUntil());
        }
        if (documentCriteria.isCreatedFromDefined()) {
            query.setParameter("createFrom", documentCriteria.getCreatedFrom());
        }
        if (documentCriteria.isVerifiedUntilDefined()) {
            query.setParameter("verifiedUntil", documentCriteria.getVerifiedUntil());
        }
        if (documentCriteria.isVerifiedFromDefined()) {
            query.setParameter("verifiedFrom", documentCriteria.getVerifiedFrom());
        }
        if (documentCriteria.isQueryDefined()) {
            query.setParameter("query", "%" + documentCriteria.getQuery() + "%");
        }
    }

    /*public DocumentDto get(DocumentNumber documentNumber) {
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
*/
}