package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.DocumentCriteria;
import pl.com.bottega.documentmanagement.api.DocumentDto;
import pl.com.bottega.documentmanagement.api.DocumentsCatalog;
import pl.com.bottega.documentmanagement.api.RequiresAuth;
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
@Component
public class JPADocumentsCatalog implements DocumentsCatalog {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @RequiresAuth(roles = "STAFF")
    public DocumentDto get(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentDto> query = builder.createQuery(DocumentDto.class);
        Root<Document> root = query.from(Document.class);
        query.where(builder.and(
                builder.equal(root.get(Document_.documentNumber), documentNumber)),
                builder.not(root.get(Document_.deleted))
        );
        selectDocumentDto(builder, query, root);
        return entityManager.createQuery(query).getSingleResult();
    }

    private void selectDocumentDto(CriteriaBuilder builder, CriteriaQuery<DocumentDto> query, Root<Document> root) {
        query.select(builder.construct(DocumentDto.class,
                root.get(Document_.documentNumber).get(DocumentNumber_.number),
                root.get(Document_.title),
                root.get(Document_.content),
                root.get(Document_.status),
                root.get(Document_.createdAt),
                root.get(Document_.verifiedAt),
                root.get(Document_.updatedAt),
                root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
                root.get(Document_.verificator).get(Employee_.employeeId).get(EmployeeId_.id)
        ));
    }

    @Override
    @RequiresAuth(roles = "STAFF")
    public Iterable<DocumentDto> find(DocumentCriteria documentCriteria) {
        checkNotNull(documentCriteria);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentDto> query = builder.createQuery(DocumentDto.class);
        Root<Document> root = query.from(Document.class);
        selectDocumentDto(builder, query, root);
        applyCriteria(documentCriteria, builder, query, root);
        return entityManager.createQuery(query).getResultList();
    }

    private void applyCriteria(DocumentCriteria documentCriteria, CriteriaBuilder builder, CriteriaQuery<DocumentDto> query, Root<Document> root) {
        Collection<Predicate> predicates = new HashSet<>();
        applyStatus(documentCriteria, builder, root, predicates);
        applyCreatedBy(documentCriteria, builder, root, predicates);
        applyVerifiedBy(documentCriteria, builder, root, predicates);
        applyCreatedFrom(documentCriteria, builder, root, predicates);
        applyCreatedUntil(documentCriteria, builder, root, predicates);
        applyVerifiedFrom(documentCriteria, builder, root, predicates);
        applyVerifiedUntil(documentCriteria, builder, root, predicates);
        applyQuery(documentCriteria, builder, root, predicates);
        applyNotDeleted(documentCriteria, builder, root, predicates);
        query.where(predicates.toArray(new Predicate[]{}));
    }

    private void applyNotDeleted(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        predicates.add(builder.not(root.get(Document_.deleted)));
    }

    private void applyQuery(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isQueryDefined()) {
            //(content like "%query%" OR title like "%query%") AND () AND () AND ()
            predicates.add(builder.or(
                    builder.like(root.get(Document_.content), "%" + documentCriteria.getQuery() + "%"),
                    builder.like(root.get(Document_.title), "%" + documentCriteria.getQuery() + "%")
            ));
        }
    }

    private void applyCreatedUntil(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isCreatedUntilDefined()) {
            predicates.add(builder.lessThanOrEqualTo(
                    root.get(Document_.createdAt), documentCriteria.getCreatedUntil()
            ));
        }
    }

    private void applyCreatedFrom(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isCreatedFromDefined()) {
            predicates.add(builder.greaterThanOrEqualTo(
                    root.get(Document_.createdAt), documentCriteria.getCreatedFrom()
            ));
        }
    }

    private void applyVerifiedUntil(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isVerifiedUntilDefined()) {
            predicates.add(builder.lessThanOrEqualTo(
                    root.get(Document_.verifiedAt), documentCriteria.getVerifiedUntil()
            ));
        }
    }

    private void applyVerifiedFrom(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isVerifiedFromDefined()) {
            predicates.add(builder.greaterThanOrEqualTo(
                    root.get(Document_.verifiedAt), documentCriteria.getVerifiedFrom()
            ));
        }
    }

    private void applyCreatedBy(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isCreatedByDefined()) {
            predicates.add(builder.equal(
                    root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
                    documentCriteria.getCreatedBy())
            );
        }
    }

    private void applyVerifiedBy(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isVerifiedByDefined()) {
            predicates.add(builder.equal(
                    root.get(Document_.verificator).get(Employee_.employeeId).get(EmployeeId_.id),
                    documentCriteria.getVerifiedBy())
            );
        }
    }

    private void applyStatus(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isStatusDefined()) {
            predicates.add(builder.equal(root.get(Document_.status), documentCriteria.getStatus()));
        }
    }

}
