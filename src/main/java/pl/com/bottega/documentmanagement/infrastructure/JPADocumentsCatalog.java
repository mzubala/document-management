package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Service;
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
import javax.print.Doc;

import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by maciuch on 12.06.16.
 */
@Service
public class JPADocumentsCatalog implements DocumentsCatalog {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @RequiresAuth
    public DocumentDto get(DocumentNumber documentNumber) {

//        "SELECT new pl.com.bottega.documentmanagement.api.DocumentDto(' d.title, d.content, d.status, d.employee.employeeId.id
//        ') + FROM Document d WHERE ()" -> do HQL, tu: symulacja tworzenia konstruktora

        checkNotNull(documentNumber);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentDto> query = builder.createQuery(DocumentDto.class);
        Root<Document> root = query.from(Document.class);
        query.where(builder.and(builder.equal(root.get(Document_.number), documentNumber), builder.isFalse(root.get(Document_.deleted))));
        uploadDocumentDto(builder, query, root);
        return entityManager.createQuery(query).getSingleResult();

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
//    @RequiresAuth
    public DocumentSearchResults find(DocumentCriteria documentCriteria) {
        checkNotNull(documentCriteria);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentDto> query = builder.createQuery(DocumentDto.class);
        Root<Document> root = query.from(Document.class);
        uploadDocumentDto(builder, query, root);
        applyCriteria(documentCriteria, builder, root, query);

        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<Document> countRoot = countQuery.from(Document.class);
        countQuery.select(builder.count(countRoot));
        applyCriteria(documentCriteria, builder, countRoot, countQuery);

        Query jpaQuery = entityManager.createQuery(query);
        Query jpaCountQuery = entityManager.createQuery(countQuery);

//        query.where(predicates.toArray(new Predicate[] {}));

        long first = (documentCriteria.getPageNumber() - 1) * documentCriteria.getPerPage();
        jpaQuery.setFirstResult((int)first);
        jpaQuery.setMaxResults(documentCriteria.getPerPage().intValue());
        return new DocumentSearchResults(jpaQuery.getResultList(), documentCriteria.getPerPage(), documentCriteria.getPageNumber(), (Long) jpaCountQuery.getSingleResult());

//        return entityManager.createQuery(query).setFirstResult((documentCriteria.getPageNumber() -1)*documentCriteria.getPerPage()).setMaxResults(documentCriteria.getPerPage()).getResultList();
    }

    private void applyCriteria(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, CriteriaQuery query) {
        Collection<Predicate> predicates = new HashSet<>();
        addDocumentsIfAreNotDeleted(documentCriteria, builder, root, predicates);
        addDocumentsIfStatusDefined(documentCriteria, builder, root, predicates);
        addDocumentsIfCreatedByDefined(documentCriteria, builder, root, predicates);
        addDocumentsIfVerifiedByDefined(documentCriteria, builder, root, predicates);
        addDocumentsIfCreatedDatesDefined(documentCriteria, builder, root, predicates);
        addDocumentsIfVerifiedDatesDefined(documentCriteria, builder, root, predicates);
        addDocumentsifQueryDefined(documentCriteria, builder, root, predicates);
        query.where(predicates.toArray(new Predicate[] {}));
    }

    private void addDocumentsIfAreNotDeleted(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        predicates.add((builder.isFalse(root.get(Document_.deleted))));
    }

    private void addDocumentsifQueryDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if(documentCriteria.isQueryDefined()) {
            predicates.add(builder.or(
                    builder.like(root.get(Document_.content), "%" + documentCriteria.getQuery() + "%"),
                    builder.like(root.get(Document_.title), "%" + documentCriteria.getQuery() + "%")
            ));
        }
    }

    private void addDocumentsIfVerifiedDatesDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if(documentCriteria.isVerifiedDatesDefined()) {
            if(documentCriteria.isVerfiedFromDefined())
                predicates.add(builder.greaterThanOrEqualTo(root.get(Document_.verifiedAt), documentCriteria.getVerifiedFrom()));
            if(documentCriteria.isVerifiedUntilDefined())
                predicates.add(builder.lessThanOrEqualTo(root.get(Document_.verifiedAt), documentCriteria.getVerifiedUntil()));
        }
    }

    private void addDocumentsIfCreatedDatesDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if(documentCriteria.isCreatedDatesDefined()) {
            if(documentCriteria.isCreatedFromDefined())
                predicates.add(builder.greaterThanOrEqualTo(root.get(Document_.createAt), documentCriteria.getCreatedFrom()));
            if(documentCriteria.isCreatedUntilDefined())
                predicates.add(builder.lessThanOrEqualTo(root.get(Document_.createAt), documentCriteria.getCreatedUntil()));
        }
    }

    private void addDocumentsIfVerifiedByDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if(documentCriteria.isVerifiedByDefined()) {
            predicates.add(builder.equal(
                    root.get(Document_.verificator).get(Employee_.employeeId).get(EmployeeId_.id),
                    documentCriteria.getVerifiedBy()
            ));
        }
    }

    private void addDocumentsIfCreatedByDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if(documentCriteria.isCreatedByDefined()) {
            predicates.add(builder.equal(
                    root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
                    documentCriteria.getCreatedBy())
            );
        }
    }

    private void addDocumentsIfStatusDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder, Root<Document> root, Collection<Predicate> predicates) {
        if(documentCriteria.isStatusDefinied()) {
            predicates.add(builder.equal(root.get(Document_.documentStatus), documentCriteria.getDocumentStatus()));
        }
    }

}
