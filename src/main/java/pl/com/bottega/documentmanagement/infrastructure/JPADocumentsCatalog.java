package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Service
public class JPADocumentsCatalog implements DocumentsCatalog {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    @RequiresAuth
    public DocumentDto get(DocumentNumber documentNumber) {

//        "SELECT new pl.com.bottega.documentmanagement.api.DocumentDto(' d.title, d.content, d.status, d.employee.employeeId.id
//        ') + FROM Document d WHERE ()" -> do HQL, tu: symulacja tworzenia konstruktora

        checkNotNull(documentNumber);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DocumentDto> query = builder.createQuery(DocumentDto.class);
        Root<Document> root = query.from(Document.class);
        query.where(builder.equal(root.get(Document_.number), documentNumber));
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

        if(documentCriteria.isStatusDefinied()) {
            predicates.add(builder.equal(root.get(Document_.documentStatus), documentCriteria.getDocumentStatus()));
        }

        if(documentCriteria.isCreatedByDefined()) {
            predicates.add(builder.equal(
                    root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
                    documentCriteria.getCreatedBy())
            );
        }

        if(documentCriteria.isCreatedDatesDefined()) {
            if(documentCriteria.isCreatedFromDefined())
                predicates.add(builder.greaterThanOrEqualTo(root.get(Document_.createAt), documentCriteria.getCreatedFrom()));
            if(documentCriteria.isCreatedUntilDefined())
                predicates.add(builder.lessThanOrEqualTo(root.get(Document_.createAt), documentCriteria.getCreatedUntil()));
        }

        if(documentCriteria.isQueryDefined()) {
            predicates.add(builder.or(
                    builder.like(root.get(Document_.content), "%" + documentCriteria.getQuery() + "%"),
                    builder.like(root.get(Document_.title), "%" + documentCriteria.getQuery() + "%")
            ));
        }

        query.where(predicates.toArray(new Predicate[] {}));
        return entityManager.createQuery(query).getResultList();
    }

}
