package pl.com.bottega.documentmanagement.infrastructure;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by bartosz.paszkowski on 31.07.2016.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration("/application.xml")
@TestPropertySource({"/jdbc-test.properties","/hibernate-test.properties"})
@WebAppConfiguration

public class JPADocumentCatalogTest {

    @Autowired // i wstrzyknieta zależność
    private DocumentsCatalog jpaDocumentsCatalog;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private PrintCostCalculator printCostCalculator;

    @Test
    @Transactional
    public void shouldFindDocumentByStatus(){
        //given
        Employee employee = new Employee("test", "test", new EmployeeId(10L));
        Document documentDraft = new Document(new DocumentNumber("1"),"draft", "draft", employee, printCostCalculator);
        Document documentVerified = new Document(new DocumentNumber("2"),"verified", "verified", employee, printCostCalculator);
        entityManager.persist(employee);
        entityManager.persist(documentDraft);
        documentVerified.verify(employee);
        entityManager.persist(documentVerified);

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1);
        documentCriteria.setPerPage(25);
        documentCriteria.setStatus(DocumentStatus.DRAFT);
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(2, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("test content", document.getContent());
        assertEquals("DRAFT", document.getStatus());

    }
    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentByStatusSqlSetup(){
        //given


        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1);
        documentCriteria.setPerPage(25);
        documentCriteria.setStatus(DocumentStatus.DRAFT);
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(2, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("test content", document.getContent());
        assertEquals("DRAFT", document.getStatus());
    }
    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentByVerifier(){
        //given

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1);
        documentCriteria.setPerPage(25);
        documentCriteria.setVerifiedBy(102L);
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(1, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals(new Long(102), document.getVerifierId());
    }
    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentByCreator(){
        //given

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1);
        documentCriteria.setPerPage(25);
        documentCriteria.setCreatedBy(100L);
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);
        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(2, documents.size());
        DocumentDto document = documents.get(1);
        assertEquals(new Long(100), document.getCreatorId());

    }
    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentCreatedFrom()throws Exception{
        //given
        String str = "2016.01.01";
        DateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date date = format.parse(str);
//TODO asercja dat
        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1);
        documentCriteria.setPerPage(25);
        documentCriteria.setCreatedFrom(date);
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(4, documents.size());
        DocumentDto document = documents.get(1);
        assertEquals(new Long(1), document.getCreatorId());
    }
    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentCreatedUntil()throws Exception{
        //given
        String str = "2016.01.10";
        DateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        Date date = format.parse(str);

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1);
        documentCriteria.setPerPage(25);
        documentCriteria.setCreatedUntil(date);
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(2, documents.size());
        DocumentDto document = documents.get(1);
        assertEquals(new Long(100), document.getCreatorId());
    }



    //given

    //when

    //then
}
