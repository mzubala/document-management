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
 * Created by Admin on 25.07.2016.
 */

//@Component
public class JPQLDocumentsCatalog implements DocumentsCatalog {

    @PersistenceContext
    private EntityManager entityManager;

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
            queryList.add(" d.getStatus =:getStatus ");
        }
        if (documentCriteria.isCreatedByDefined()) {
            queryList.add(" d.getCreator.employeeId.id =:creatorId ");
        }
        if (documentCriteria.isVerifiedByDefined()) {
            queryList.add(" d.getVerificator.employeeId.id =:verificatorId ");
        }


        if (documentCriteria.isCreatedUntilDefined()) {
            queryList.add(" d.createdAt < :createUntil ");
        }
        if (documentCriteria.isCreatedFromDefined()) {
            queryList.add(" d.createdAt > :createFrom ");
        }

        if (documentCriteria.isVerifiedUntilDefined()) {
            queryList.add(" d.getVerifiedAt < :verifiedUntil ");
        }
        if (documentCriteria.isVerifiedFromDefined()) {
            queryList.add(" d.getVerifiedAt > :verifiedFrom ");
        }

        if (documentCriteria.isQueryDefined()) {
            queryList.add(" (d.getTitle LIKE :query OR d.getContent LIKE :query) ");
        }

        String jpql = "SELECT new pl.com.bottega.documentmanagement.api.DocumentDto(" +
                "d.documentNumber.getNumber," +
                "d.getTitle, d.getContent," +
                "d.getStatus, d.createdAt, d.getVerifiedAt, d.updatedAt," +
                "d.getCreator.employeeId.id, d.getVerificator.employeeId.id) " +
                "FROM Document d WHERE d.isDeleted = false ";

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

}