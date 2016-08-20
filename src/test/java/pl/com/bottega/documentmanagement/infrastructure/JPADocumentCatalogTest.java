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
import java.text.ParseException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * Created by maciuch on 31.07.16.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration({"/application.xml", "/mock-auth-context.xml"})
@TestPropertySource({"/jdbc-test.properties", "/hibernate-test.properties"})
@WebAppConfiguration
public class JPADocumentCatalogTest {

    @Autowired
    private DocumentsCatalog jpaDocumentsCatalog;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @Transactional
    public void shouldFindDocumentByStatus() {
        //given
        Employee employee = new Employee("test", "test", new EmployeeId(10L));
        Document documentDraft = new Document(new DocumentNumber("1"), "draft", "draft", employee);
        Document documentVerified = new Document(new DocumentNumber("2"), "verified", "verified", employee);
        entityManager.persist(employee);
        entityManager.persist(documentDraft);
        documentVerified.verify(employee);
        entityManager.persist(documentVerified);

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1L);
        documentCriteria.setPerPage(25L);
        documentCriteria.setStatus(DocumentStatus.DRAFT);
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(1, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("draft", document.getContent());
        assertEquals("DRAFT", document.getStatus());
    }


    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentByDraftStatusSQLSetup() {
        //given

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1l);
        documentCriteria.setPerPage(25l);
        documentCriteria.setStatus(DocumentStatus.DRAFT);
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(3, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("draft content", document.getContent());
        assertEquals("DRAFT", document.getStatus());
        document = documents.get(1);
        assertEquals("second draft content", document.getContent());
        assertEquals("DRAFT", document.getStatus());
        document = documents.get(2);
        assertEquals("third draft content", document.getContent());
        assertEquals("DRAFT", document.getStatus());
    }

    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentByVerifiedStatusSQLSetup() {
        //given

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1l);
        documentCriteria.setPerPage(25l);
        documentCriteria.setStatus(DocumentStatus.VERIFIED);
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(2, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("verified content", document.getContent());
        assertEquals("VERIFIED", document.getStatus());
        document = documents.get(1);
        assertEquals("second verified content", document.getContent());
        assertEquals("VERIFIED", document.getStatus());
    }

    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentByCreateFromDateSQLSetup() throws ParseException {
        //given
        DateFormat dateFormat = DateFormat.getDateInstance();

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1l);
        documentCriteria.setPerPage(25l);
        documentCriteria.setCreatedFrom(dateFormat.parse("2016-02-01"));
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(3, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("second draft content", document.getContent());
        document = documents.get(1);
        assertEquals("second verified content", document.getContent());
        document = documents.get(2);
        assertEquals("third draft content", document.getContent());
    }

    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentByCreateUntilDateSQLSetup() throws ParseException {
        //given
        DateFormat dateFormat = DateFormat.getDateInstance();

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1l);
        documentCriteria.setPerPage(25l);
        documentCriteria.setCreatedUntil(dateFormat.parse("2016-02-01"));
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(2, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("draft content", document.getContent());
        document = documents.get(1);
        assertEquals("verified content", document.getContent());
    }

    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentByCreateDatesDateSQLSetup() throws ParseException {
        //given
        DateFormat dateFormat = DateFormat.getDateInstance();

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1l);
        documentCriteria.setPerPage(25l);
        documentCriteria.setCreatedFrom(dateFormat.parse("2016-02-01"));
        documentCriteria.setCreatedUntil(dateFormat.parse("2016-03-01"));
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(3, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("second draft content", document.getContent());
        document = documents.get(1);
        assertEquals("second verified content", document.getContent());
        document = documents.get(2);
        assertEquals("third draft content", document.getContent());
    }

    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentByVerifiedFromDateSQLSetup() throws ParseException {
        //given
        DateFormat dateFormat = DateFormat.getDateInstance();

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1l);
        documentCriteria.setPerPage(25l);
        documentCriteria.setVerifiedFrom(dateFormat.parse("2016-02-01"));
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(1, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("second verified content", document.getContent());
    }

    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentByVerifiedUntilDateSQLSetup() throws ParseException {
        //given
        DateFormat dateFormat = DateFormat.getDateInstance();

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1l);
        documentCriteria.setPerPage(25l);
        documentCriteria.setVerifiedUntil(dateFormat.parse("2016-02-01"));
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(1, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("verified content", document.getContent());
    }

    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentByVerifyDatesSQLSetup() throws ParseException {
        //given
        DateFormat dateFormat = DateFormat.getDateInstance();

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1l);
        documentCriteria.setPerPage(25l);
        documentCriteria.setVerifiedFrom(dateFormat.parse("2016-01-01"));
        documentCriteria.setVerifiedUntil(dateFormat.parse("2016-01-04"));
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(1, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("verified content", document.getContent());
    }

    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentCreatedBydSQLSetup() {
        //given

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1l);
        documentCriteria.setPerPage(25l);
        documentCriteria.setCreatedBy(100l);
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(2, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("draft content", document.getContent());
        document = documents.get(1);
        assertEquals("second verified content", document.getContent());
    }

    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentVerifiedBydSQLSetup() {
        //given

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1l);
        documentCriteria.setPerPage(25l);
        documentCriteria.setVerifiedBy(200l);
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(1, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("verified content", document.getContent());
        assertEquals("VERIFIED", document.getStatus());
    }

    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentByQuerySQLSetup() {
        //given

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1l);
        documentCriteria.setPerPage(25l);
        documentCriteria.setQuery("second");
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(2, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("second draft content", document.getContent());
        document = documents.get(1);
        assertEquals("second verified content", document.getContent());
    }

    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldFindDocumentByPartialQuerySQLSetup() {
        //given

        //when
        DocumentCriteria documentCriteria = new DocumentCriteria();
        documentCriteria.setPageNumber(1l);
        documentCriteria.setPerPage(25l);
        documentCriteria.setQuery("draf");
        DocumentSearchResults results = jpaDocumentsCatalog.find(documentCriteria);

        //then
        assertEquals(new Long(1), results.getTotalPages());
        List<DocumentDto> documents = Lists.newArrayList(results.getDocuments());
        assertEquals(3, documents.size());
        DocumentDto document = documents.get(0);
        assertEquals("draft content", document.getContent());
        document = documents.get(1);
        assertEquals("second draft content", document.getContent());
        document = documents.get(2);
        assertEquals("third draft content", document.getContent());
    }

    @Sql("/fixtures/documents.sql")
    @Test
    @Transactional
    public void shouldGetDocumentSQLSetup() {
        //given

        //when
        DocumentDto document = jpaDocumentsCatalog.get(new DocumentNumber("13A"));

        //then
        assertNotNull(document);
        assertEquals("verified content", document.getContent());
        assertEquals("VERIFIED", document.getStatus());
    }


}
