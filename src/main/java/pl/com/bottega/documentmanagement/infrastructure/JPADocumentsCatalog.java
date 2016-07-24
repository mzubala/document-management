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
@Component
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
        query.select(builder.construct(DocumentDto.class,
                root.get(Document_.documentNumber).get(DocumentNumber_.number),
                root.get(Document_.title),
                root.get(Document_.content),
                root.get(Document_.documentStatus),
                root.get(Document_.createdAt),
                root.get(Document_.verificatedAt),
                root.get(Document_.updatedAt),
                root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
                root.get(Document_.verificator).get(Employee_.employeeId).get(EmployeeId_.id)
        ));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public Iterable<DocumentDto> find(DocumentCriteria documentCriteria) {
        checkNotNull(documentCriteria);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentDto> query = builder.createQuery(DocumentDto.class);
        Root<Document> root = query.from(Document.class);
        Collection<Predicate> predicates = new HashSet<>();
        query.select(builder.construct(DocumentDto.class,
                root.get(Document_.documentNumber).get(DocumentNumber_.number),
                root.get(Document_.title),
                root.get(Document_.content),
                root.get(Document_.documentStatus),
                root.get(Document_.createdAt),
                root.get(Document_.verificatedAt),
                root.get(Document_.updatedAt),
                root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
                root.get(Document_.verificator).get(Employee_.employeeId).get(EmployeeId_.id)
        ));

        if (documentCriteria.isDocumentNumberDefined()) {
            findByDocNr(documentCriteria, builder, root, predicates);
        }

        if (documentCriteria.isQueryDefined()) {
            //(content like "%query%" OR title like "%query%") AND () AND () AND ()
            findByTitleOrContent(documentCriteria, builder, root, predicates);
        }

        if (documentCriteria.isStatusDefined()) {
            predicates.add(builder.equal(root.get(Document_.documentStatus), documentCriteria.getStatus()));
        }

        if (documentCriteria.isCreatedByDefined()) {
            findByCreator(documentCriteria, builder, root, predicates);
        }

        if (documentCriteria.isCreatesDatesDefined()) {
            if (documentCriteria.isCreatedFromDefined()) {
                findByCreatedFrom(documentCriteria, builder, root, predicates);
            }
            if (documentCriteria.isCreatedUntilDefined()) {
                findByCreatedUntil(documentCriteria, builder, root, predicates);
            }
        }

        if (documentCriteria.isVerifiedByDefined()) {
            findByVerificator(documentCriteria, builder, root, predicates);
        }

        if (documentCriteria.isVerifiesDatesDefined()) {
            if (documentCriteria.isVerifiedFromDefined()) {
                findByVerifiedFrom(documentCriteria, builder, root, predicates);
            }
            if (documentCriteria.isVerifiedUntilDefined()) {
                findByVerifiedUntil(documentCriteria, builder, root, predicates);
            }
        }
        if (documentCriteria.isUpdatedDatesDefined()) {
            if (documentCriteria.isUpdatedFromDefined()) {
                findByUpdatedFrom(documentCriteria, builder, root, predicates);
            }
            if (documentCriteria.isUpdatedUntilDefined()) {
                findByUpdatedUntil(documentCriteria, builder, root, predicates);
            }
        }

        query.where(predicates.toArray(new Predicate[]{}));
            return entityManager.createQuery(query).getResultList();
    }

    private void findByUpdatedUntil(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        predicates.add(builder.lessThanOrEqualTo(
                root.get(Document_.updatedAt), documentCriteria.getUpdatedUntil()
        ));
    }

    private void findByUpdatedFrom(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        predicates.add(builder.greaterThanOrEqualTo(
                root.get(Document_.updatedAt), documentCriteria.getUpdatedFrom()
        ));
    }

    private void findByVerifiedUntil(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        predicates.add(builder.lessThanOrEqualTo(
                root.get(Document_.verificatedAt), documentCriteria.getVerifiedUntil()
        ));
    }

    private void findByVerifiedFrom(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        predicates.add(builder.greaterThanOrEqualTo(
                root.get(Document_.verificatedAt), documentCriteria.getVerifiedFrom()
        ));
    }

    private void findByVerificator(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        predicates.add(builder.equal(
                root.get(Document_.verificator).get(Employee_.employeeId).get(EmployeeId_.id),
                documentCriteria.getVerifiedBy()
        ));
    }

    private void findByCreatedUntil(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        predicates.add(builder.lessThanOrEqualTo(
                root.get(Document_.createdAt), documentCriteria.getCreatedUntil()
        ));
    }

    private void findByCreatedFrom(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        predicates.add(builder.greaterThanOrEqualTo(
                root.get(Document_.createdAt), documentCriteria.getCreatedFrom()
        ));
    }

    private void findByCreator(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        predicates.add(builder.equal(
                root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
                documentCriteria.getCreatedBy())
        );
    }

    private void findByTitleOrContent(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        predicates.add(builder.or(
                builder.like(root.get(Document_.content), "%" + documentCriteria.getContent() + "%"),
                builder.like(root.get(Document_.title), "%" + documentCriteria.getTitle() + "%")
        ));
    }

    private void findByDocNr(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        predicates.add(builder.equal(
                root.get(Document_.documentNumber), documentCriteria.getDocumentNumber()
        ));
    }

}
