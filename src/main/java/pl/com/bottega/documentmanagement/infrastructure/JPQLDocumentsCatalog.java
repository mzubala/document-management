package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.api.*;
import pl.com.bottega.documentmanagement.api.DocumentCriteria;
import pl.com.bottega.documentmanagement.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dell on 2016-07-18.
 */
//@Service
public class JPQLDocumentsCatalog implements DocumentsCatalog {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
//    @RequiresAuth
    public DocumentDto get(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        String jpql = "SELECT NEW pl.com.bottega.documentmanagement.api.DocumentDto(d.number.number, d.title, d.content, " +
                "d.documentStatus, d.createAt, d.verifiedAt, d.updatedAt, d.creator.employeeId.id, d.verificator.employeeId.id) FROM Document d " +
                "WHERE d.number =:documentNumber AND d.deleted = false";

        Query query = entityManager.createQuery(jpql);
        query.setParameter("documentNumber", documentNumber);
        return (DocumentDto) query.getSingleResult();

//        return entityManager.createQuery(jpql, DocumentDto.class).setParameter("documentNumber", documentNumber.getNumber()).getSingleResult();
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
//        if (documentCriteria.isStatusDefined()) {
//            queryList.add(" d.status =:status ");
//        }
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
//        if (documentCriteria.isVerifiedFromDefined()) {
//            queryList.add(" d.verifiedAt > :verifiedFrom ");
//        }

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
//        if (documentCriteria.isStatusDefined()) {
//            query.setParameter("status", documentCriteria.getStatus());
//        }
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
//        if (documentCriteria.isVerifiedFromDefined()) {
//            query.setParameter("verifiedFrom", documentCriteria.getVerifiedFrom());
//        }
        if (documentCriteria.isQueryDefined()) {
            query.setParameter("query", "%" + documentCriteria.getQuery() + "%");
        }
    }


//    private void selectDocumentDto(CriteriaBuilder builder, CriteriaQuery<DocumentDto> query, Root<Document> root) {
//        query.select(builder.construct(DocumentDto.class,
//                root.get(Document_.number).get(DocumentNumber_.number),
//                root.get(Document_.title),
//                root.get(Document_.content),
//                root.get(Document_.documentStatus),
//                root.get(Document_.createAt),
//                root.get(Document_.verifiedAt),
//                root.get(Document_.updatedAt),
//                root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
//                root.get(Document_.verificator).get(Employee_.employeeId).get(EmployeeId_.id)
//        ));
//    }
//
//
//    @Override
//    @Transactional
//    @RequiresAuth
//    public DocumentSearchResults findByEmployeeIds(pl.com.bottega.documentmanagement.api.DocumentCriteria documentCriteria) {
//        checkNotNull(documentCriteria);
//        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<DocumentDto> query = builder.createQuery(DocumentDto.class);
//        Root<Document> root = query.from(Document.class);
//        selectDocumentDto(builder, query, root);
//
//        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
//        Root<Document> countRoot = countQuery.from(Document.class);
//        countQuery.select(builder.count(countRoot));
//        applyCriteria(documentCriteria, builder, countRoot, countQuery);
//
//        Query jpaQuery = entityManager.createQuery(query);
//        Query jpaCountQuery = entityManager.createQuery(countQuery);
//
//        query.where(predicates.toArray(new Predicate[] {}));
//
//        long first = (documentCriteria.getPageNumber() - 1) * documentCriteria.getPerPage();
//        jpaQuery.setFirstResult((int)first);
//        jpaQuery.setMaxResults(documentCriteria.getPerPage().intValue());
//
//
//        return new DocumentSearchResults(jpaQuery.getResultList(), documentCriteria.getPerPage(), documentCriteria.getPageNumber(), (Long) jpaCountQuery.getSingleResult());
//    }
//
//    private void applyCriteria(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, CriteriaQuery query) {
//        Collection<Predicate> predicates = new HashSet<>();
//        addDocumentsIfAreNotDeleted(documentCriteria, builder, root, predicates);
//        addDocumentsIfStatusDefined(documentCriteria, builder, root, predicates);
//        addDocumentsIfCreatedByDefined(documentCriteria, builder, root, predicates);
//        addDocumentsIfVerifiedByDefined(documentCriteria, builder, root, predicates);
//        addDocumentsIfCreatedDatesDefined(documentCriteria, builder, root, predicates);
//        addDocumentsIfVerifiedDatesDefined(documentCriteria, builder, root, predicates);
//        addDocumentsifQueryDefined(documentCriteria, builder, root, predicates);
//        query.where(predicates.toArray(new Predicate[] {}));
//    }
//
//    private void addDocumentsIfAreNotDeleted(pl.com.bottega.documentmanagement.api.DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
//        predicates.add((builder.isFalse(root.get(Document_.deleted))));
//    }
//
//    private void addDocumentsifQueryDefined(pl.com.bottega.documentmanagement.api.DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
//        if(documentCriteria.isQueryDefined()) {
//            predicates.add(builder.or(
//                    builder.like(root.get(Document_.content), "%" + documentCriteria.getQuery() + "%"),
//                    builder.like(root.get(Document_.title), "%" + documentCriteria.getQuery() + "%")
//            ));
//        }
//    }
//
//    private void addDocumentsIfVerifiedDatesDefined(pl.com.bottega.documentmanagement.api.DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
//        if(documentCriteria.isVerifiedDatesDefined()) {
//            if(documentCriteria.isVerfiedFromDefined())
//                predicates.add(builder.greaterThanOrEqualTo(root.get(Document_.verifiedAt), documentCriteria.getVerifiedFrom()));
//            if(documentCriteria.isVerifiedUntilDefined())
//                predicates.add(builder.lessThanOrEqualTo(root.get(Document_.verifiedAt), documentCriteria.getVerifiedUntil()));
//        }
//    }
//
//    private void addDocumentsIfCreatedDatesDefined(pl.com.bottega.documentmanagement.api.DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
//        if(documentCriteria.isCreatedDatesDefined()) {
//            if(documentCriteria.isCreatedFromDefined())
//                predicates.add(builder.greaterThanOrEqualTo(root.get(Document_.createAt), documentCriteria.getCreatedFrom()));
//            if(documentCriteria.isCreatedUntilDefined())
//                predicates.add(builder.lessThanOrEqualTo(root.get(Document_.createAt), documentCriteria.getCreatedUntil()));
//        }
//    }
//
//    private void addDocumentsIfVerifiedByDefined(pl.com.bottega.documentmanagement.api.DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
//        if(documentCriteria.isVerifiedByDefined()) {
//            predicates.add(builder.equal(
//                    root.get(Document_.verificator).get(Employee_.employeeId).get(EmployeeId_.id),
//                    documentCriteria.getVerifiedBy()
//            ));
//        }
//    }
//
//    private void addDocumentsIfCreatedByDefined(pl.com.bottega.documentmanagement.api.DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
//        if(documentCriteria.isCreatedByDefined()) {
//            predicates.add(builder.equal(
//                    root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
//                    documentCriteria.getCreatedBy())
//            );
//        }
//    }
//
//    private void addDocumentsIfStatusDefined(pl.com.bottega.documentmanagement.api.DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
//        if(documentCriteria.isStatusDefinied()) {
//            predicates.add(builder.equal(root.get(Document_.documentStatus), documentCriteria.getDocumentStatus()));
//        }
//    }
}
