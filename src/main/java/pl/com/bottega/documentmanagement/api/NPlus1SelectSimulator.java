package pl.com.bottega.documentmanagement.api;

import com.google.common.collect.Sets;
import org.springframework.beans.factory.parsing.SourceExtractor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;

/**
 * Created by paulina.pislewicz on 2016-07-30.
 */
@Service
public class NPlus1SelectSimulator {

    @PersistenceContext
    private EntityManager entityManager; //pozwala pobierać i zapisywać rzeczy do bazy

   @Transactional //ponieważ zapisuje do bazy
    public void insertTestData(){
       Employee employee = new Employee(randomString(), randomString(), new EmployeeId(38693l));
       entityManager.persist(employee);
        for (int i =0; i<1000; i++){
            Document d = new Document(new DocumentNumber(randomString()), randomString(), randomString(), employee);
            d.tag(Sets.newHashSet(new Tag("one"),new Tag("two"),new Tag("three")));
        entityManager.persist(d);
        }
    }
    @Transactional
    public void simulate () {
        Query query = entityManager.createQuery("FROM Document", Document.class).setMaxResults(10);
        List<Document> documents = query.getResultList();
        for (Document d : documents) {
            System.out.print(d.toString()+ " ");
            for (Tag t : d.tags()) {
                System.out.print(d.tags()+ "");
            }
            System.out.println();
        }
    }

    @Transactional
    public Document getDocument(){
        Query query = entityManager.createQuery("FROM Document");
        query.setMaxResults(1);
        return (Document) query.getResultList().get(0);
    }

    private String randomString(){
        return UUID.randomUUID().toString();
    }
}
