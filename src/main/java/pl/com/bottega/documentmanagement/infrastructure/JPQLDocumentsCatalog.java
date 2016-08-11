package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.api.DocumentCriteria;
import pl.com.bottega.documentmanagement.api.DocumentDto;
import pl.com.bottega.documentmanagement.api.DocumentSearchResults;
import pl.com.bottega.documentmanagement.api.DocumentsCatalog;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by bartosz.paszkowski on 15.07.2016.
 */

//@Component
public class JPQLDocumentsCatalog implements DocumentsCatalog{
//    @Override
//    public DocumentDto get(DocumentNumber documentNumber) {
//        return null;
//    }
//
//    @Override
//    public Iterable<DocumentDto> find(DocumentCriteria documentCriteria) {
//        return null;
//    }


    //@PersistenceContext
    private EntityManager entityManager;

    @Override
    public DocumentDto get(DocumentNumber documentNumber) {
        checkNotNull(documentNumber);
        String query = "select new pl.com.bottega.documentmanagement.api.DocumentDto(d.documentNumber.number, d.title, " +
                "d.content, d.documentStatus, d.createdAt, d.verificationAt, d.updatedAt, d.creator.employeeId.id, " +
                "d.verifier.employeeId.id)" +
                "from Document d where d.documentNumber = :documentNumber";
        return entityManager.createQuery(query,DocumentDto.class)
                .setParameter("documentNumber",documentNumber.getNumber()).getSingleResult();
    }

    @Override
    public DocumentSearchResults find(DocumentCriteria documentCriteria) {
        checkNotNull(documentCriteria);
//        return entityManager.createQuery("select new pl.com.bottega.documentmanagement.api.DocumentDto" +
//                "(d.documentNumber.number, " +
//                "d.title, " +
//                "d.content, " +
//                "d.status, " +
//                "d.createdAt, " +
//                "d.verificationAt, " +
//                "d.updateAt, " +
//                "d.creatorId.employeeId.id, " +
//                "d.verifierId.employeeId.id)" +
//                "from Document d " +
//                "where d.documentNumber = :documentNumber " +
//                "and d.title = :title " +
//                "and d.content = :content " +
//                "and d.status = :status " +
//                "and d.createdAt = :createdAt " +
//                "and d.verificationAt = :verificationAt " +
//                "and d.updateAt = :updateAt " +
//                "and d.creatorId = :creatorId " +
//                "and d.verifierId = :verifierId",
//                DocumentDto.class)
//                //.setParameter("title", documentCriteria.getQuery())
//                //.setParameter("content", documentCriteria.getQuery())
//                .setParameter("status",documentCriteria.getStatus())
//                //.setParameter("createdAt", documentCriteria.getCreatedFrom())
//               //.setParameter("verificationAt", documentCriteria.getVerifiedFrom())
//                .getResultList();
        return null;
    }
}
