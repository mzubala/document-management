package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.*;
import pl.com.bottega.documentmanagement.api.DocumentCriteria;
import pl.com.bottega.documentmanagement.api.DocumentDto;
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

/**
 * Created by Wojciech Winiarski on 12.06.16.
 */
@Component
public class JPADocumentsCatalog implements DocumentsCatalog {


    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DocumentDto get(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);

        //  "SELECTS new ...DocumentDto("+
        //        "d.title, d.content, d.status....."+
        //      "FROM Document d"+
        //    "WHERE() AND ()"; HSQL korzystanie z innego api wylaczyc @componet
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentDto> query = builder.createQuery(DocumentDto.class);
        Root<Document> root = query.from(Document.class);
        query.where(builder.equal(root.get(Document_.documentNumber), documentNumber));
        selectDocumentDto(builder, query, root);
        return entityManager.createQuery(query).getSingleResult();

    }

    @Override
    public DocumentSearchResults find(DocumentCriteria documentCriteria) {
        checkNotNull(documentCriteria);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentDto> query = builder.createQuery(DocumentDto.class);
        Root<Document> root = query.from(Document.class);
        selectDocumentDto(builder, query, root);
        applyCriteria(documentCriteria, root, builder, query);

        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        Root<Document> countRoot = countQuery.from(Document.class);
        countQuery.select(builder.count(countRoot));
        applyCriteria(documentCriteria, countRoot, builder, countQuery);


        Query jpaQuery = entityManager.createQuery(query);
        Query jpaCountQuery = entityManager.createQuery(countQuery);

        long first = (documentCriteria.getPageNumber() - 1) * documentCriteria.getPerPage();
        jpaQuery.setFirstResult((int) first);
        jpaQuery.setMaxResults(documentCriteria.getPerPage().intValue());


        return new DocumentSearchResults(jpaQuery.getResultList(),
                documentCriteria.getPerPage(),
                documentCriteria.getPageNumber(), (Long) jpaCountQuery.getSingleResult());
    }

    private void selectDocumentDto(CriteriaBuilder builder, CriteriaQuery<DocumentDto> query, Root<Document> root) {

        query.select(builder.construct(DocumentDto.class,
                root.get(Document_.documentNumber).get(DocumentNumber_.number),
                root.get(Document_.title),
                root.get(Document_.content),
                root.get(Document_.documentStatus),
                root.get(Document_.createAt),
                root.get(Document_.verificatedAt),
                root.get(Document_.updatedAt),
                root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
                root.get(Document_.veryficator).get(Employee_.employeeId).get(EmployeeId_.id),
                root.get(Document_.deleted)

        ));


    }

    private void addDocumentIsDocumentIsNotDeleted(CriteriaBuilder builder,
                                                   Root<Document> root, Collection<Predicate> predicates) {

        predicates.add(builder.isFalse(root.get(Document_.deleted)));

    }

    private void addDocumentIsStatusIsDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder,
                                              Root<Document> root, Collection<Predicate> predicates) {

        if (documentCriteria.isStatusDefine())
            predicates.add(builder.equal(root.get(Document_.documentStatus), documentCriteria.getStatus()));


    }

    private void addDocumentIsCreatedIsDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder,
                                               Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isCreatedByDefined())
            predicates.add(builder.equal(root.get(Document_.creator).get(
                    Employee_.employeeId).get(EmployeeId_.id),
                    documentCriteria.getCreatedBy()));

    }

    private void addDocumentIsVerifiedIsDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder,
                                                Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isVerifyByDefined())
            predicates.add(builder.equal(root.get(Document_.veryficator).get(Employee_.employeeId).get(EmployeeId_.id),
                    documentCriteria.getVerifiedBy()));


    }

    private void addDocumentIsCratedDateDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder,
                                                Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isCreatedDatesDefined()) {
            if (documentCriteria.isCreatedFromDefined()) {
                predicates.add(builder.greaterThanOrEqualTo(root.get(Document_.createAt),
                        documentCriteria.getCreatedFrom()));
            }
            if (documentCriteria.isCreatedUntilDefined()) {
                predicates.add(builder.lessThanOrEqualTo(root.get(Document_.createAt),
                        documentCriteria.getCreatedUntil()));
            }

        }
    }

    private void addDocumentIsVerifyDateDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder,
                                                Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isVerifyDateDefined()) {
            if (documentCriteria.isVerifyFromDefined()) {
                predicates.add(builder.greaterThanOrEqualTo(root.get(Document_.verificatedAt),
                        documentCriteria.getVerifiedFrom()));
            }
            if (documentCriteria.isVerifiedUntilDefined()) {
                predicates.add(builder.lessThanOrEqualTo(root.get(Document_.verificatedAt),
                        documentCriteria.getVerifiedUntil()));
            }

        }

    }

    public void addDocumentIsQueryDefined(DocumentCriteria documentCriteria, CriteriaBuilder builder,
                                          Root<Document> root, Collection<Predicate> predicates) {
        if (documentCriteria.isQueryDefined()) {
            predicates.add(builder.or(
                    builder.like(root.get(Document_.content), "%" +
                            documentCriteria.getQuery() + "%"),
                    builder.like(root.get(Document_.title), "%" +
                            documentCriteria.getQuery() + "%")));

        }

    }

    private void applyCriteria(DocumentCriteria documentCriteria,
                               Root<Document> root, CriteriaBuilder builder, CriteriaQuery query) {
        Collection<Predicate> predicates = new HashSet<>();
        addDocumentIsDocumentIsNotDeleted(builder, root, predicates);
        addDocumentIsStatusIsDefined(documentCriteria, builder, root, predicates);
        addDocumentIsCreatedIsDefined(documentCriteria, builder, root, predicates);
        addDocumentIsVerifiedIsDefined(documentCriteria, builder, root, predicates);
        addDocumentIsCratedDateDefined(documentCriteria, builder, root, predicates);
        addDocumentIsVerifyDateDefined(documentCriteria, builder, root, predicates);
        addDocumentIsQueryDefined(documentCriteria, builder, root, predicates);
        query.where(predicates.toArray(new Predicate[]{}));

    }


    //  if (documentCriteria.isStatusDefine()) {
    //      predicates.add(builder.equal(root.get(Document_.documentStatus), documentCriteria.getStatus()));
    //  }
//        if (documentCriteria.isCreatedByDefined()) {
//            predicates.add(builder.equal(root.get(Document_.creator).get(
//                    Employee_.employeeId).get(EmployeeId_.id),
//                    documentCriteria.getCreatedBy()));
//        }
//        if (documentCriteria.isVerifyByDefined()) {
//            predicates.add(builder.equal(root.get(Document_.veryficator).get(Employee_.employeeId).get(EmployeeId_.id),
//                    documentCriteria.getVerifiedBy()));
//        }
//        if (documentCriteria.isCreatedDatesDefined()) {
//            if (documentCriteria.isCreatedFromDefined()) {
//                predicates.add(builder.greaterThanOrEqualTo(root.get(Document_.createAt),
//                        documentCriteria.getCreatedFrom()));
//            }
//            if (documentCriteria.isCreatedUntilDefined()) {
//                predicates.add(builder.lessThanOrEqualTo(root.get(Document_.createAt),
//                        documentCriteria.getCreatedUntil()));
//            }
//
//        }
//        if (documentCriteria.isVerifyDateDefined()) {
//            if (documentCriteria.isVerifyFromDefined()) {
//                predicates.add(builder.greaterThanOrEqualTo(root.get(Document_.verificatedAt),
//                        documentCriteria.getVerifiedFrom()));
//            }
//            if (documentCriteria.isVerifiedUntilDefined()) {
//                predicates.add(builder.lessThanOrEqualTo(root.get(Document_.verificatedAt),
//                        documentCriteria.getVerifiedUntil()));
//            }
//
//        }
//        if (documentCriteria.isQueryDefined()) {
//            predicates.add(builder.or(
//                    builder.like(root.get(Document_.content), "%" +
//                            documentCriteria.getQuery() + "%"),
//                    builder.like(root.get(Document_.title), "%" +
//                            documentCriteria.getQuery() + "%")));
//
//        }
}


