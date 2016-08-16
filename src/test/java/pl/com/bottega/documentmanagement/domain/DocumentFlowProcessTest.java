package pl.com.bottega.documentmanagement.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.documentmanagement.api.DocumentFactory;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.api.UserManager;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static pl.com.bottega.documentmanagement.domain.DocumentStatus.DRAFT;
import static pl.com.bottega.documentmanagement.domain.DocumentStatus.VERIFIED;

/**
 * Created by Dell on 2016-08-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class DocumentFlowProcessTest {

    private final static Long EPS = 2L * 1000L;

    private DocumentFlowProcess documentFlowProcess;
    private String anyContent = "Test content";
    private String anyTitle = "Test title";
    private String newTitle = "New title";
    private String newContent = "New Content";

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private UserManager userManager;

    @Mock
    private DocumentNumberGenerator documentNumberGenerator;

    @Mock
    private DocumentFactory documentFactory;

    @Mock
    private DocumentNumber documentNumber;

    @Mock
    private Employee employee;

    @Mock
    private Document document;

    @Before
    public void setUp() {
        documentFlowProcess = new DocumentFlowProcess(documentRepository, userManager, documentNumberGenerator, documentFactory);
    }

    @Test
    public void shouldCreateDocument() {
        Document document = new Document(documentNumber, anyContent, anyTitle, employee);
        when(documentNumberGenerator.generate()).thenReturn(documentNumber);
        when(userManager.currentEmployee()).thenReturn(employee);
        when(documentFactory.create(documentNumber, anyContent, anyTitle, employee)).thenReturn(document);

        DocumentNumber result = documentFlowProcess.create(anyTitle, anyContent);

        verify(documentRepository).save(document);
        assertNotNull(result);
        assertEquals(anyTitle, document.getTitle());
        assertEquals(anyContent, document.getContent());
        assertEquals(employee, document.getCreator());
        assertEquals(documentNumber, document.getNumber());
        assertEquals(DRAFT, document.getDocumentStatus());
        assertFalse(document.getDeleted());
    }

    @Test
    public void shouldRequireTitleForCreatingDocument() {
        try {
            documentFlowProcess.create(null, anyContent);
        } catch (NullPointerException ex) {
            return;
        }
        fail("NullPointerException expected");
    }

    @Test
    public void shouldRequireContentForCreatingDocument() {
        try {
            documentFlowProcess.create(anyTitle, null);
        } catch (NullPointerException ex) {
            return;
        }
        fail("NullPointerException expected");
    }

    @Test
    public void shouldChangeDocument() {
        Document document = new Document(documentNumber, anyContent, anyTitle, employee);
        when(documentRepository.load(documentNumber)).thenReturn(document);
        doNothing().when(mock(Document.class)).change(newTitle, newContent);

        documentFlowProcess.change(documentNumber, newTitle, newContent);

        verify(documentRepository).save(document);
        assertEquals(newTitle, document.getTitle());
        assertEquals(newContent, document.getContent());
        assertEquals(documentNumber, document.getNumber());
        assertNotEquals(anyContent, document.getContent());
    }

    @Test
    public void shouldRequireDocumentNumberForChangingDocuments() {
        try {
            documentFlowProcess.change(null, newTitle, newContent);
        }
        catch (NullPointerException ex) {
            return;
        }
        fail("NullPointerExceptionExpected");
    }

    @Test
    public void shouldRequireTitleForChangingDocuments() {
        try {
            documentFlowProcess.change(documentNumber, null, newContent);
        }
        catch (NullPointerException ex) {
            return;
        }
        fail("NullPointerExceptionExpected");
    }

    @Test
    public void shouldRequireContentForChangingDocuments() {
        try {
            documentFlowProcess.change(documentNumber, newTitle, null);
        }
        catch (NullPointerException ex) {
            return;
        }
        fail("NullPointerExceptionExpected");
    }

    @Test
    public void shouldVerifyDocument() {
        Document document = new Document(documentNumber, anyContent, anyTitle, employee);
        when(documentRepository.load(documentNumber)).thenReturn(document);
        when(userManager.currentEmployee()).thenReturn(employee);
        doNothing().when(mock(Document.class)).verify(employee);

        documentFlowProcess.verify(documentNumber);

        assertEquals(VERIFIED, document.getDocumentStatus());
        assertTrue(Math.abs(new Date().getTime() - document.getVerifiedAt().getTime()) < EPS);
        assertEquals(employee, document.getVerificator());
    }

    @Test
    public void shouldRequireDocumentNumberForVerifyingDocuments() {
        try {
            documentFlowProcess.verify(null);
        }
        catch (NullPointerException ex) {
            return;
        }
        fail("NullPointerExceptionExpected");
    }

    @Test
    public void shouldArchiveDocument() {
        Document document = new Document(documentNumber, anyContent, anyTitle, employee);
        when(documentRepository.load(documentNumber)).thenReturn(document);
        when(userManager.currentEmployee()).thenReturn(employee);
        doNothing().when(mock(Document.class)).delete(employee);

        documentFlowProcess.archive(documentNumber);

        assertTrue(document.getDeleted());
        assertEquals(employee, document.getDeletedBy());
    }


    @Test
    public void shouldRequireDocumentNumberForDocumentArchive() {
        try {
            documentFlowProcess.archive(null);
        }
        catch (NullPointerException ex) {
            return;
        }
        fail("NullPointerExceptionExpected");
    }
}
