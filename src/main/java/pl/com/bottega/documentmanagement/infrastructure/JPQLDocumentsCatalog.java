package pl.com.bottega.documentmanagement.infrastructure;

import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.api.*;
import pl.com.bottega.documentmanagement.domain.*;
import pl.com.bottega.documentmanagement.domain.DocumentCriteria;

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
 * Created by Dell on 2016-07-18.
 */
@Service
public class JPQLDocumentsCatalog implements DocumentsCatalog {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
//    @RequiresAuth
    public DocumentDto get(DocumentNumber documentNumber) {

        checkNotNull(documentNumber);
        String query = "SELECT NEW pl.com.bottega.documentmanagement.api.DocumentDto(d.number.number, d.title, d.content, " +
                "d.documentStatus, d.createAt, d.verifiedAt, d.updatedAt, d.creator.employeeId.id, d.verificator.employeeId.id) FROM Document d " +
                "WHERE d.number = ?1";

        return entityManager.createQuery(query, DocumentDto.class).setParameter(1, documentNumber.getNumber()).getSingleResult();
    }

    private void uploadDocumentDto(CriteriaBuilder builder, CriteriaQuery<DocumentDto> query, Root<Document> root) {
        query.select(builder.construct(DocumentDto.class,
                root.get(Document_.number).get(DocumentNumber_.number),
                root.get(Document_.title),
                root.get(Document_.content),
                root.get(Document_.documentStatus),
                root.get(Document_.createAt),
                root.get(Document_.verifiedAt),
                root.get(Document_.updatedAt),
                root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
                root.get(Document_.verificator).get(Employee_.employeeId).get(EmployeeId_.id)
        ));
    }


    @Override
    @Transactional
//    @RequiresAuth
    public Iterable<DocumentDto> find(pl.com.bottega.documentmanagement.api.DocumentCriteria documentCriteria) {
        checkNotNull(documentCriteria);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentDto> query = builder.createQuery(DocumentDto.class);
        Root<Document> root = query.from(Document.class);
        Collection<Predicate> predicates = new HashSet<>();
        uploadDocumentDto(builder, query, root);

        addDocumentsIfAreNotDeleted(documentCriteria, builder, root, predicates);
        addDocumentsIfStatusDefined(documentCriteria, builder, root, predicates);
        addDocumentsIfCreatedByDefined(documentCriteria, builder, root, predicates);
        addDocumentsIfVerifiedByDefined(documentCriteria, builder, root, predicates);
        addDocumentsIfCreatedDatesDefined(documentCriteria, builder, root, predicates);
        addDocumentsIfVerifiedDatesDefined(documentCriteria, builder, root, predicates);
        addDocumentsifQueryDefined(documentCriteria, builder, root, predicates);

        query.where(predicates.toArray(new Predicate[] {}));
        return entityManager.createQuery(query).getResultList();
    }

    private void addDocumentsIfAreNotDeleted(pl.com.bottega.documentmanagement.api.DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        predicates.add((builder.isFalse(root.get(Document_.deleted))));
    }

    private void addDocumentsifQueryDefined(pl.com.bottega.documentmanagement.api.DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if(documentCriteria.isQueryDefined()) {
            predicates.add(builder.or(
                    builder.like(root.get(Document_.content), "%" + documentCriteria.getQuery() + "%"),
                    builder.like(root.get(Document_.title), "%" + documentCriteria.getQuery() + "%")
            ));
        }
    }

    private void addDocumentsIfVerifiedDatesDefined(pl.com.bottega.documentmanagement.api.DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if(documentCriteria.isVerifiedDatesDefined()) {
            if(documentCriteria.isVerfiedFromDefined())
                predicates.add(builder.greaterThanOrEqualTo(root.get(Document_.verifiedAt), documentCriteria.getVerifiedFrom()));
            if(documentCriteria.isVerifiedUntilDefined())
                predicates.add(builder.lessThanOrEqualTo(root.get(Document_.verifiedAt), documentCriteria.getVerifiedUntil()));
        }
    }

    private void addDocumentsIfCreatedDatesDefined(pl.com.bottega.documentmanagement.api.DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if(documentCriteria.isCreatedDatesDefined()) {
            if(documentCriteria.isCreatedFromDefined())
                predicates.add(builder.greaterThanOrEqualTo(root.get(Document_.createAt), documentCriteria.getCreatedFrom()));
            if(documentCriteria.isCreatedUntilDefined())
                predicates.add(builder.lessThanOrEqualTo(root.get(Document_.createAt), documentCriteria.getCreatedUntil()));
        }
    }

    private void addDocumentsIfVerifiedByDefined(pl.com.bottega.documentmanagement.api.DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if(documentCriteria.isVerifiedByDefined()) {
            predicates.add(builder.equal(
                    root.get(Document_.verificator).get(Employee_.employeeId).get(EmployeeId_.id),
                    documentCriteria.getVerifiedBy()
            ));
        }
    }

    private void addDocumentsIfCreatedByDefined(pl.com.bottega.documentmanagement.api.DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if(documentCriteria.isCreatedByDefined()) {
            predicates.add(builder.equal(
                    root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
                    documentCriteria.getCreatedBy())
            );
        }
    }

    private void addDocumentsIfStatusDefined(pl.com.bottega.documentmanagement.api.DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if(documentCriteria.isStatusDefinied()) {
            predicates.add(builder.equal(root.get(Document_.documentStatus), documentCriteria.getDocumentStatus()));
        }
    }
}
