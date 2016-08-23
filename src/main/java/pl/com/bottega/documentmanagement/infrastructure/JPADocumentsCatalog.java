package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.*;
import pl.com.bottega.documentmanagement.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.HashSet;

import static com.google.common.base.Preconditions.checkNotNull;


@Component
public class JPADocumentsCatalog implements DocumentsCatalog {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @RequiresAuth(roles = "STAFF")
    public DocumentDto get(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);

        /*"SELECT new pl.com.bottega.documentmamagement.api.DocumentDto("+
                "d.title, d.content,d.status,d.employeeId.id"+
                ")"+
                "FROM Document d "+
                "WHERE () AND () AND ()";

        */
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

    @Override
    @RequiresAuth(roles = "STAFF")
    public DocumentSearchResults find(DocumentCriteria documentCriteria) {
        checkNotNull(documentCriteria);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentDto> query = builder.createQuery(DocumentDto.class);
        Root<Document> root = query.from(Document.class);

        selectDocumentDto(builder,query,root);
        applyCriteria(documentCriteria,builder,query,root);

        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<Document> countRoot = countQuery.from(Document.class);
        countQuery.select(builder.count(countRoot));
        applyCriteria(documentCriteria,builder,countQuery,countRoot);


        Query jpaQuery = entityManager.createQuery(query);
        Query jpaCountQuery = entityManager.createQuery(countQuery);

        int first = (documentCriteria.getPageNumber() - 1)*documentCriteria.getPerPage();
        jpaQuery.setFirstResult(first);
        jpaQuery.setMaxResults(documentCriteria.getPerPage());

        return new DocumentSearchResults(jpaQuery.getResultList(),   documentCriteria.getPerPage(),
                 documentCriteria.getPageNumber(), (Long) jpaCountQuery.getSingleResult());
        //return entityManager.createQuery(query).setFirstResult(documentCriteria.getPageNumber()).setMaxResults(documentCriteria.getPerPage()).getResultList();
    }

    private void selectDocumentDto(CriteriaBuilder builder, CriteriaQuery<DocumentDto> query, Root<Document> root){
        query.select(builder.construct(DocumentDto.class,
                root.get(Document_.documentNumber).get(DocumentNumber_.number),
                root.get(Document_.title),
                root.get(Document_.content),
                root.get(Document_.documentStatus),
                root.get(Document_.createdAt),
                root.get(Document_.verificationAt),
                root.get(Document_.updatedAt),
                root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
                root.get(Document_.verifier).get(Employee_.employeeId).get(EmployeeId_.id),
                root.get(Document_.deleted)

        ));
    }

    private void applyCriteria(DocumentCriteria documentCriteria,CriteriaBuilder builder, CriteriaQuery query, Root<Document> root){
        Collection<Predicate> predicates = new HashSet<>();
        addDocumentIsDocumentIsNotDeleted(documentCriteria,builder,root,predicates);
        addDocumentIfIsStatusDefined(documentCriteria,builder,root,predicates);
        addDocumentIfisCreatedByDefined(documentCriteria,builder,root,predicates);
        addDocumentIfisVerifiedByDefined(documentCriteria,builder,root,predicates);
        addDocumentIfisCreatedDatesDefined(documentCriteria,builder,root,predicates);
        addDocumentIfisVerifiedDateDefined(documentCriteria,builder,root,predicates);
        addDocumentIfisQueryDefined(documentCriteria,builder,root,predicates);
        query.where(predicates.toArray(new Predicate[]{}));
    }

    private void addDocumentIsDocumentIsNotDeleted(DocumentCriteria documentCriteria, CriteriaBuilder builder,
                                                   Root<Document> root,Collection<Predicate> predicates){
        predicates.add(builder.isFalse(root.get(Document_.deleted)));
    }

    private void addDocumentIfIsStatusDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder,
                                              Root<Document> root,Collection<Predicate> predicates){
        if (documentCriteria.isStatusDefined()) {
            predicates.add(builder.equal(root.get(Document_.documentStatus), documentCriteria.getStatus()));
        }
    }
    private void addDocumentIfisCreatedByDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder,
                                                 Root<Document> root,Collection<Predicate> predicates){
        if (documentCriteria.isCreatedByDefined()) {
            predicates.add(builder.equal(
                    root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
                    documentCriteria.getCreatedBy()));
        }
    }
    private void addDocumentIfisVerifiedByDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder,
                                                  Root<Document> root,Collection<Predicate> predicates){
        if (documentCriteria.isVerifiedByDefined()){
            predicates.add(builder.equal(root.get(Document_.verifier).get(Employee_.employeeId).get(EmployeeId_.id),
                    documentCriteria.getVerifiedBy()));

        }
    }
    private void addDocumentIfisCreatedDatesDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder,
                                                    Root<Document> root,Collection<Predicate> predicates){
        if (documentCriteria.isCreatedDatesDefined()) {
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
    }

    private void addDocumentIfisVerifiedDateDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder,
                                                    Root<Document> root,Collection<Predicate> predicates){
        if (documentCriteria.isVerifiedDateDefined()){
            if (documentCriteria.ifVerifiedFromDefined()){
                predicates.add(builder.greaterThanOrEqualTo(
                        root.get(Document_.verificationAt),documentCriteria.getVerifiedFrom()
                ));
            }
            if (documentCriteria.isVerifiedUntilDefined()){
                predicates.add(builder.lessThanOrEqualTo(
                        root.get(Document_.verificationAt), documentCriteria.getVerifiedUntil()));
            }
        }
    }
    private void addDocumentIfisQueryDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder,
                                             Root<Document> root,Collection<Predicate> predicates){
        if (documentCriteria.isQueryDefined()) {
            //(content like "%query%" OR title like "%query%") AND () AND () AND ()
            predicates.add(builder.or(
                    builder.like(root.get(Document_.content), "%" + documentCriteria.getQuery() + "%"),
                    builder.like(root.get(Document_.title), "%" + documentCriteria.getQuery() + "%")
            ));
        }
    }

    //         if (documentCriteria.isStatusDefined()) {
//            predicates.add(builder.equal(root.get(Document_.documentStatus), documentCriteria.getStatus()));
//        }

//        if (documentCriteria.isCreatedByDefined()) {
//            predicates.add(builder.equal(
//                    root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
//                    documentCriteria.getCreatedBy())
//            );
//        }

//        if (documentCriteria.isVerifiedByDefined()){
//            predicates.add(builder.equal(root.get(Document_.verifier).get(Employee_.employeeId).get(EmployeeId_.id),
//                    documentCriteria.getVerifiedBy()));
//
//        }

//        if (documentCriteria.isCreatedDatesDefined()) {
//            if (documentCriteria.isCreatedFromDefined()) {
//                predicates.add(builder.greaterThanOrEqualTo(
//                        root.get(Document_.createdAt), documentCriteria.getCreatedFrom()
//                ));
//            }
//            if (documentCriteria.isCreatedUntilDefined()) {
//                predicates.add(builder.lessThanOrEqualTo(
//                        root.get(Document_.createdAt), documentCriteria.getCreatedUntil()
//                ));
//            }
//        }

//        if (documentCriteria.isVerifiedDateDefined()){
//            if (documentCriteria.ifVerifiedFromDefined()){
//                predicates.add(builder.greaterThanOrEqualTo(
//                        root.get(Document_.verificationAt),documentCriteria.getVerifiedFrom()
//                ));
//            }
//            if (documentCriteria.isVerifiedUntilDefined()){
//                predicates.add(builder.lessThanOrEqualTo(
//                        root.get(Document_.verificationAt), documentCriteria.getVerifiedUntil()));
//            }
//        }

//        if (documentCriteria.isQueryDefined()) {
//            //(content like "%query%" OR title like "%query%") AND () AND () AND ()
//            predicates.add(builder.or(
//                    builder.like(root.get(Document_.content), "%" + documentCriteria.getQuery() + "%"),
//                    builder.like(root.get(Document_.title), "%" + documentCriteria.getQuery() + "%")
//            ));
//        }
}
