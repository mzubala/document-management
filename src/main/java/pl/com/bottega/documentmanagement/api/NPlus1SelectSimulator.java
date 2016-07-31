package pl.com.bottega.documentmanagement.api;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

/**
 * Created by maciuch on 30.07.16.
 */
@Service
public class NPlus1SelectSimulator {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void insertTestData() {
        Employee employee = new Employee(randomString(), randomString(), new EmployeeId(12382L));
        entityManager.persist(employee);
        for(int i = 0; i < 1000; i++) {
            Document d = new Document(
                new DocumentNumber(randomString()), randomString(), randomString(), employee
            );
            d.tag(Sets.newHashSet(new Tag("one"), new Tag("two"), new Tag("three")));
            entityManager.persist(d);
        }

    }

    @Transactional
    public void simulate() {
        Query query = entityManager.createQuery("FROM Document d JOIN fetch d.tags", Document.class).setMaxResults(10);
        List<Document> docuemnts = query.getResultList();
        for(Document d : docuemnts) {
            System.out.print(d.toString() + " ");
            for(Tag t : d.tags()) {
                System.out.print(t.toString() + " ");
            }
            System.out.println();
        }
    }

    @Transactional
    public Document getDocument() {
        Query query = entityManager.createQuery("FROM Document d");
        query.setMaxResults(1);
        Document d = (Document) query.getResultList().get(0);
        System.out.println(d.getVerificator().toString());
        System.out.println(d.tags().size());
        return d;
    }

    private String randomString() {
        return UUID.randomUUID().toString();
    }

}
