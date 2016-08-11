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
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Wojciech Winiarski on 31.07.2016.
 */
@RunWith(SpringRunner.class)//przed uruchomieniem testu pojawi sie kontekst sprinowy
@ContextConfiguration("/application.xml")
@TestPropertySource({"/jdbc-test.properties", "/hibernate-test.properties"})
@WebAppConfiguration
public class JPADocumentCatalogTest {

    @Autowired
    private DocumentsCatalog jpaDocumentsCatalog;
    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void shouldFindDocumentByStatus(){

        //given
        Employee employee = new Employee("test", "pw", new EmployeeId(10L));
        Document draft = new Document(new DocumentNumber("1"), "DRAFT", "DRAFT", employee);
        Document verified = new Document(new DocumentNumber("2"), "VERIFIED", "VERIFIED", employee);
        entityManager.persist(employee);
        entityManager.persist(draft);
        verified.verify(employee);
        entityManager.persist(verified);

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPerPage(25L);
        documentCriteria.setPageNumber(1L);
        documentCriteria.setStatus(DocumentStatus.DRAFT);
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);
        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(1, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("DRAFT", document.getContent());
        assertEquals("DRAFT", document.getStatus());

    }

    @Test
    @Transactional
    @Sql("/fixtures/document.sql")
    public void shouldFindDocumentSqlStatusDraft(){

        //given


        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPerPage(25L);
        documentCriteria.setPageNumber(1L);
        documentCriteria.setStatus(DocumentStatus.DRAFT);
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);
        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(1, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("content", document.getContent());
        assertEquals("DRAFT", document.getStatus());

    }
    @Test
    @Transactional
    @Sql("/fixtures/document.sql")
    public void shouldFindDocumentSqlStatusVerified(){
        //given


        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPerPage(25L);
        documentCriteria.setPageNumber(1L);
        documentCriteria.setStatus(DocumentStatus.VERIFIED);
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);
        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(1, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("content", document.getContent());
        assertEquals("VERIFIED", document.getStatus());

    }
    @Transactional
    @Sql("/fixtures/document.sql")
    @Test
    public  void shouldFindDocumentSqlVerifiedBy(){
        //given


        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPerPage(25L);
        documentCriteria.setPageNumber(1L);
        documentCriteria.setVerifiedBy(1123L);

        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);
        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        DocumentDto document = documents.get(0);
        assertEquals(1, documents.size());
        assertEquals(new Long(1123), document.getVerificatorId());

    }
    @Transactional
    @Sql("/fixtures/document.sql")
    @Test
    public  void shouldFindDocumentSqlCreatedBy(){
        //given


        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPerPage(25L);
        documentCriteria.setPageNumber(1L);
        documentCriteria.setCreatedBy(1123L);

        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);
        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        DocumentDto document = documents.get(0);
        assertEquals(2, documents.size());
        assertEquals(new Long(1123), document.getCreatorId());


    }
    //TODO
//    @Transactional
//    @Sql("/fixtures/document.sql")
//    @Test
//    public  void shouldFindDocumentSqlCreatedFrom() {
//        //given
//
//
//        //when
//        DocumentCriteria documentCriteria = new DocumentCriteria();
//        documentCriteria.setPerPage(25L);
//        documentCriteria.setPageNumber(1L);
//        documentCriteria.setCreatedFrom(new Date (2016,0,1));
//        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);
//        //then
//        assertEquals(new Long(1), results.getTotalPages());
//        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
//        DocumentDto document = documents.get(0);
//        assertEquals(2, documents.size());
//        assertDate(new Long(1123), document.getCreatorId());
//        ??????????????????????
//    }

}
