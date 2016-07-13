package pl.com.bottega.documentmanagement.infrastructure;

import pl.com.bottega.documentmanagement.api.DocumentCriteria;
import pl.com.bottega.documentmanagement.api.DocumentsCatalog;
import pl.com.bottega.documentmanagement.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

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
        CriteriaBuilder builder = entityManager.getCriteriaBuilder(); // tworzymy buildera
        CriteriaQuery <DocumentDto> query = builder.createQuery(DocumentDto.class); //tworzymy kwerendę zwracającą obiekt typu Dto
        Root<Document> root = query.from(Document.class); //. mówimy skąd te dane mają być pobierane
        query.where(builder.equal(root.get(Document_.documentNumber), documentNumber)); // //definiujemy warunki wyszukiwania jedynym warunkiem jest to, że DocumentNr ma być równy zadanemu numerowi. Poza ewqual może być greater, less, like etc
        query.select(builder.construct(DocumentDto.class,  // mówimy co chcemy wyciągnąć przy użyciu metamodelu- jest to możliwe bo stworzyliśmy konstrkutor wyciagamy rzeczy z konstruktora
                root.get(Document_.documentNumber).get(DocumentNumber_.number), //idziemy zgodnie z polami z konstruktora
                root.get(Document_.title),
                root.get(Document_.content),
                root.get(Document_.documentStatus),
                root.get(Document_.createdAt),
                root.get(Document_.verificatedAt),
                root.get(Document_.updatedAt),
                root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
                root.get(Document_.verificator).get(Employee_.employeeId).get(EmployeeId_.id)

        ));
        return entityManager.createQuery(query).getSingleResult(); //mówimy EnityManagerowi by stworzył query i dobieramy się do wynikow od razu zwracając wynik
    }
//TODO: zaimplementować pozostałe warunki + refactioring, by każdy if był w osobnej metodzie
    @Override
    public List<DocumentDto> find(DocumentCriteria documentCriteria) {
        checkNotNull(documentCriteria);
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery <DocumentDto> query = builder.createQuery(DocumentDto.class);
        Root<Document> root = query.from(Document.class);
        Collection<Predicate> predicates = new HashSet<>(); //tworzę zbiór predykatów
        if(documentCriteria.isStatusDefined()){
            predicates.add(builder.equal(root.get(Document_.documentStatus), documentCriteria.getStatus())); //dodajemy predykat sprawdzający status dokumentu
        }
        if (documentCriteria.isCreatedByDefined()){
            predicates.add(builder.equal(
                    root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
                    documentCriteria.getCreatedBy())
            );
        }
        if (documentCriteria.isCreatesDatesDefined()){
            if(documentCriteria.isCreatedFromDefined()) {
                predicates.add(builder.greaterThanOrEqualTo(
                        root.get(Document_.createdAt), documentCriteria.getCreatedFrom()
                ));
            }
            if (documentCriteria.isCreatedUntilDefined()){
                predicates.add((builder.lessThanOrEqualTo(
                        root.get(Document_.createdAt), documentCriteria.getCreatedUntil()
                )));
            }
        }
        if (documentCriteria.isQueryDefined()) {
            //w SQL byłoby (content like "%que  ry%" OR title like "%query%") AND () AND ()
            predicates.add(builder.or(
                    builder.like(root.get(Document_.content), "%" + documentCriteria.getQuery() + "%"), //pierwszy człon
                    builder.like(root.get(Document_.title), "%" + documentCriteria.getQuery() + "%") //drugi człon dołączony spójnikiem OR (bo taką mamy metodę)
            ));
        }
        if (documentCriteria.isVerifiedByDefined()){
            predicates.add(builder.equal(
                    root.get(Document_.verificator).get(Employee_.employeeId).get(EmployeeId_.id),
                    documentCriteria.getVerifiedBy()
            ));
        }
        if (documentCriteria.isVerifiesDatesDefined()){
            if (documentCriteria.isVerifiedFromDefined()) {
                predicates.add(builder.greaterThanOrEqualTo(
                        root.get(Document_.verificatedAt), documentCriteria.getVerifiedFrom()
                ));
            }
            if(documentCriteria.isVerifiedUntilDefined()){
                predicates.add(builder.lessThanOrEqualTo(
                        root.get(Document_.verificatedAt), documentCriteria.getVerifiedUntil()
                ));
            }
        }
        query.where(predicates.toArray(new Predicate[]{}));
        query.select(builder.construct(DocumentDto.class,  // mówimy co chcemy wyciągnąć przy użyciu metamodelu- jest to możliwe bo stworzyliśmy konstrkutor wyciagamy rzeczy z konstruktora
                root.get(Document_.documentNumber).get(DocumentNumber_.number), //idziemy zgodnie z polami z konstruktora
                root.get(Document_.title),
                root.get(Document_.content),
                root.get(Document_.documentStatus),
                root.get(Document_.createdAt),
                root.get(Document_.verificatedAt),
                root.get(Document_.updatedAt),
                root.get(Document_.creator).get(Employee_.employeeId).get(EmployeeId_.id),
                root.get(Document_.verificator).get(Employee_.employeeId).get(EmployeeId_.id)
        ));
        return entityManager.createQuery(query).getResultList(); //z entitymanagera tworzymy query i zwracamy listę wyników
    }

}
