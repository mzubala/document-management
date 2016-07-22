package pl.com.bottega.documentmanagement.infrastructure;

import pl.com.bottega.documentmanagement.api.DocumentCriteria;
import pl.com.bottega.documentmanagement.api.DocumentDto;
import pl.com.bottega.documentmanagement.api.DocumentsCatalog;
import pl.com.bottega.documentmanagement.domain.*;

import javax.persistence.EntityManager;
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
//@Component
public class JPADocumentsCatalog implements DocumentsCatalog {

    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public DocumentDto get(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentDto> query = builder.createQuery(DocumentDto.class);
        Root<Document> root = query.from(Document.class);
       // query.where(builder.not(root.get(Document_.deleted)));
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
            predicates.add(builder.equal(
                    root.get(Document_.documentNumber), documentCriteria.getDocumentNumber()
            ));
        }

        if (documentCriteria.isQueryDefined()) {
            //(content like "%query%" OR title like "%query%") AND () AND () AND ()
            predicates.add(builder.or(
                    builder.like(root.get(Document_.content), "%" + documentCriteria.getContent() + "%"),
                    builder.like(root.get(Document_.title), "%" + documentCriteria.getTitle() + "%")
            ));
        }

        if (documentCriteria.isStatusDefined()) {
            predicates.add(builder.equal(root.get(Document_.documentStatus), documentCriteria.getStatus()));
        }

        if (documentCriteria.isCreatedByDefined()) {
            predicates.add(builder.equal(
                    root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
                    documentCriteria.getCreatedBy())
            );
        }

        if (documentCriteria.isCreatesDatesDefined()) {
            if (documentCriteria.isCreatedFromDefined()) {
                predicates.add(builder.greaterThanOrEqualTo(
                        root.get(Document_.createdAt), documentCriteria.getCreatedFrom()
                ));
            }
            if (documentCriteria.isCreatedUntilDefined()) {
                predicates.add(builder.lessThanOrEqualTo(
                        root.get(Document_.createdAt), documentCriteria.getCreatedUntil()
                ));
            }
        }

        if (documentCriteria.isVerifiedByDefined()) {
            predicates.add(builder.equal(
                    root.get(Document_.verificator).get(Employee_.employeeId).get(EmployeeId_.id),
                    documentCriteria.getVerifiedBy()
            ));
        }

        if (documentCriteria.isVerifiesDatesDefined()) {
            if (documentCriteria.isVerifiedFromDefined()) {
                predicates.add(builder.greaterThanOrEqualTo(
                        root.get(Document_.verificatedAt), documentCriteria.getVerifiedFrom()
                ));
            }
            if (documentCriteria.isVerifiedUntilDefined()) {
                predicates.add(builder.lessThanOrEqualTo(
                        root.get(Document_.verificatedAt), documentCriteria.getVerifiedUntil()
                ));
            }
        }
        if (documentCriteria.isUpdatedDatesDefined()) {
            if (documentCriteria.isUpdatedFromDefined()) {
                predicates.add(builder.greaterThanOrEqualTo(
                        root.get(Document_.updatedAt), documentCriteria.getUpdatedFrom()
                ));
            }
            if (documentCriteria.isUpdatedUntilDefined()) {
                predicates.add(builder.lessThanOrEqualTo(
                        root.get(Document_.updatedAt), documentCriteria.getUpdatedUntil()
                ));
            }

        }
        query.where(predicates.toArray(new Predicate[]{}));
        return entityManager.createQuery(query).getResultList();
    }

}
