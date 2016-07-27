package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.DocumentCriteria;
import pl.com.bottega.documentmanagement.api.DocumentDto;
import pl.com.bottega.documentmanagement.api.DocumentsCatalog;
import pl.com.bottega.documentmanagement.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by maciuch on 12.06.16.
 */

public class JPADocumentsCatalog implements DocumentsCatalog {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DocumentDto get(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<DocumentDto> query = builder.createQuery(DocumentDto.class);
        Root<Document> root = query.from(Document.class);
        query.where(builder.equal(root.get(Document_.documentNumber), documentNumber));
        querySelect(builder, query, root);
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public Iterable<DocumentDto> find(DocumentCriteria documentCriteria) {
        checkNotNull(documentCriteria);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentDto> query = builder.createQuery(DocumentDto.class);
        Root<Document> root = query.from(Document.class);
        Collection<Predicate> predicates = new HashSet<>();

        querySelect(builder, query, root);

        predicates.add(builder.equal(root.get(Document_.deleted), false));

        fillStatement(documentCriteria, builder, root, predicates);

        query.where(predicates.toArray(new Predicate[]{}));
        return entityManager.createQuery(query).getResultList();
    }

    private void fillStatement(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        selectByStatus(documentCriteria, builder, root, predicates);
        selectByCreator(documentCriteria, builder, root, predicates);
        selectByVerificator(documentCriteria, builder, root, predicates);
        selectByCreatedDate(documentCriteria, builder, root, predicates);
        selectByVerifiedDate(documentCriteria, builder, root, predicates);
        selectByContent(documentCriteria, builder, root, predicates);
    }

    private void selectByContent(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isQueryDefined()) {
            predicates.add(builder.or(builder.like(root.get(Document_.content), "%" + documentCriteria.getQuery() + "%"),
                    builder.like(root.get(Document_.title), "%" + documentCriteria.getQuery() + "%")));
        }
    }

    private void selectByVerifiedDate(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isVerifiedDateDefined()) {
            if (documentCriteria.isVerifiedFromDefined()) {
                predicates.add(builder.greaterThanOrEqualTo(root.get(Document_.verifiedAt), documentCriteria.getVerifiedFrom()));
            }
            if (documentCriteria.isVerifiedUntilDefined()) {
                predicates.add(builder.lessThanOrEqualTo(root.get(Document_.verifiedAt), documentCriteria.getVerifiedUntil()));
            }
        }
    }

    private void selectByCreatedDate(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isCreatedDateDefined()) {
            if (documentCriteria.isCreatedFromDefined()) {
                predicates.add(builder.greaterThanOrEqualTo(root.get(Document_.createdAt), documentCriteria.getCreatedFrom()));
            }
            if (documentCriteria.isCreatedUntilDefined()) {
                predicates.add(builder.lessThanOrEqualTo(root.get(Document_.createdAt), documentCriteria.getCreatedUntil()));
            }
        }
    }

    private void selectByVerificator(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isVerifiedByDefined()) {
            predicates.add(builder.equal(root.get(Document_.verificator).get(Employee_.employeeId).get(EmployeeId_.id), documentCriteria.getVerifiedBy()));
        }
    }

    private void selectByCreator(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isCreatedByDefined()) {
            predicates.add(builder.equal(root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id), documentCriteria.getCreatedBy()));
        }
    }

    private void selectByStatus(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isStatusDefined()) {
            predicates.add(builder.equal(root.get(Document_.documentStatus), documentCriteria.getDocumentStatus()));
        }
    }

    private void querySelect(CriteriaBuilder builder, CriteriaQuery<DocumentDto> query, Root<Document> root) {
        query.select(builder.construct(DocumentDto.class,
                root.get(Document_.documentNumber).get(DocumentNumber_.number),
                root.get(Document_.title),
                root.get(Document_.content),
                root.get(Document_.documentStatus),
                root.get(Document_.deleted),
                root.get(Document_.createdAt),
                root.get(Document_.verifiedAt),
                root.get(Document_.updatedAt),
                root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
                root.get(Document_.verificator).get(Employee_.employeeId).get(EmployeeId_.id)));
    }
}
