package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.DocumentCriteria;
import pl.com.bottega.documentmanagement.api.DocumentDto;
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

@Component
public class JPQLDocumentsCatalog implements DocumentsCatalog {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DocumentDto get(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        String jpql = "select new pl.com.bottega.documentmanagement.api.DocumentDto(" +
                "d.documentNumber.number," +
                "d.title, d.content," +
                "d.documentStatus, d.createdAt, d.verifiedAt, d.updatedAt," +
                "d.creator.employeeId.id, d.verificator.employeeId.id) " +
                "from Document d where d.documentNumber=:documentNumber " +
                "AND d.deleted = false";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("documentNumber", documentNumber);
        return (DocumentDto) query.getSingleResult();
    }

    @Override
    public Iterable<DocumentDto> find(DocumentCriteria documentCriteria) {
        checkNotNull(documentCriteria);
        String jpql = buildQuery(documentCriteria);
        Query query = entityManager.createQuery(jpql);
        fillStatement(documentCriteria, query);
        return query.getResultList();
    }

    private String buildQuery(DocumentCriteria documentCriteria) {
        List<String> queryList = new ArrayList<>();
        if (documentCriteria.isStatusDefined()) {
            queryList.add(" d.documentStatus =:status ");
        }
        if (documentCriteria.isCreatedByDefined()) {
            queryList.add(" d.creator.employeeId.id =:creatorId ");
        }
        if (documentCriteria.isVerifiedByDefined()) {
            queryList.add(" d.verificator.employeeId.id =:verificatorId ");
        }

        if (documentCriteria.isCreatedDateDefined()) {
            if (documentCriteria.isCreatedFromDefined() && documentCriteria.isCreatedUntilDefined()) {
                queryList.add(" d.createdAt BETWEEN :createFrom AND :createUntil ");
            } else if (documentCriteria.isCreatedUntilDefined()) {
                queryList.add(" d.createdAt < :createUntil ");
            } else {
                queryList.add(" d.createdAt > :createFrom ");
            }
        }

        if (documentCriteria.isVerifiedDateDefined()) {
            if (documentCriteria.isVerifiedFromDefined() && documentCriteria.isVerifiedUntilDefined()) {
                queryList.add(" d.verifiedAt BETWEEN :verifiedFrom AND :verifiedUntil ");
            } else if (documentCriteria.isVerifiedUntilDefined()) {
                queryList.add(" d.verifiedAt < :verifiedUntil ");
            } else {
                queryList.add(" d.verifiedAt > :verifiedFrom ");
            }
        }

        if (documentCriteria.isQueryDefined()) {
            queryList.add(" (d.title LIKE :query OR d.content LIKE :query) ");
        }

        String jpql = "SELECT new pl.com.bottega.documentmanagement.api.DocumentDto(" +
                "d.documentNumber.number," +
                "d.title, d.content," +
                "d.documentStatus, d.createdAt, d.verifiedAt, d.updatedAt," +
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
            query.setParameter("status", documentCriteria.getDocumentStatus());
        }
        if (documentCriteria.isCreatedByDefined()) {
            query.setParameter("creatorId", documentCriteria.getCreatedBy());
        }
        if (documentCriteria.isVerifiedByDefined()) {
            query.setParameter("verificatorId", documentCriteria.getVerifiedBy());
        }
        if (documentCriteria.isCreatedDateDefined()) {
            if (documentCriteria.isCreatedFromDefined() && documentCriteria.isCreatedUntilDefined()) {
                query.setParameter("createFrom", documentCriteria.getCreatedFrom());
                query.setParameter("createUntil", documentCriteria.getCreatedUntil());
            } else if (documentCriteria.isCreatedUntilDefined()) {
                query.setParameter("createUntil", documentCriteria.getCreatedUntil());
            } else {
                query.setParameter("createFrom", documentCriteria.getCreatedFrom());
            }
        }

        if (documentCriteria.isVerifiedDateDefined()) {
            if (documentCriteria.isVerifiedFromDefined() && documentCriteria.isVerifiedUntilDefined()) {
                query.setParameter("verifiedFrom", documentCriteria.getVerifiedFrom());
                query.setParameter("verifiedUntil", documentCriteria.getVerifiedUntil());
            } else if (documentCriteria.isVerifiedUntilDefined()) {
                query.setParameter("verifiedUntil", documentCriteria.getVerifiedUntil());
            } else {
                query.setParameter("verifiedFrom", documentCriteria.getVerifiedFrom());
            }
        }

        if (documentCriteria.isQueryDefined()) {
            query.setParameter("query", "%" + documentCriteria.getQuery() + "%");
        }
    }
}
