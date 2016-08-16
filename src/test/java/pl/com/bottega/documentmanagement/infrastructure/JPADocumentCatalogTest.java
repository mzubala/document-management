package pl.com.bottega.documentmanagement.infrastructure;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.api.DocumentCriteria;
import pl.com.bottega.documentmanagement.api.DocumentDto;
import pl.com.bottega.documentmanagement.api.DocumentSearchResults;
import pl.com.bottega.documentmanagement.api.DocumentsCatalog;
import pl.com.bottega.documentmanagement.domain.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Dell on 2016-07-31.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration("/application.xml")
@TestPropertySource({"/jdbc-test.properties", "/hibernate-test.properties"})
@WebAppConfiguration
@Sql("/fixtures/documents.sql")
public class JPADocumentCatalogTest {

    @Autowired
    private DocumentsCatalog jpaDocumentsCatalog;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void shouldFindDocumentByStatus() {
        //given
        Employee employee = new Employee("test login", "test password", new EmployeeId(876L));
        Document documentDraft = new Document(new DocumentNumber("1"), "draft", "draft", employee);
        Document documentVerified = new Document(new DocumentNumber("2"), "verified", "verified", employee);
        entityManager.persist(employee);
        entityManager.persist(documentDraft);
        documentVerified.verify(employee);
        entityManager.persist(documentVerified);

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1l);
        documentCriteria.setPerPage(25l);
        documentCriteria.setDocumentStatus(DocumentStatus.DRAFT);
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(2, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("draft", document.getContent());
        assertEquals("DRAFT", document.getStatus());
    }

    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentByStatusSQLSetup() {
        //given

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1l);
        documentCriteria.setPerPage(25l);
        documentCriteria.setDocumentStatus(DocumentStatus.DRAFT);
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(1, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("draft content", document.getContent());
        assertEquals("DRAFT", document.getStatus());
    }

}
