package pl.com.bottega.documentmanagement.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.documentmanagement.domain.*;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * Created by Admin on 13.08.2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class DocumentFlowProcessTest {

    @Mock
    private DocumentNumberGenerator anyDocumentNumberGenerator;
    @Mock
    private EmployeeRepository anyEmployeeRepository;
    @Mock
    private DocumentRepository anyDocumentRepository;
    @Mock
    private UserManager anyUserManager;
    @Mock
    private Employee anyEmployee;
    @Mock
    private DocumentNumber anyDocumentNumber;
    @InjectMocks
    private Document anyDocument;
    @Spy
    private Set<Reader> readers = new HashSet<>();
    @Mock
    private Reader anyReader;
    @Mock
    private DocumentFactory anyDocumentFactory;
    @Mock
    private EmployeeFactory anyEmployeeFactory;
    @Mock
    private EmployeeId anyEmployeeId;


    private String anyContent = "content";
    private String anyTitle = "title";
    private DocumentFlowProcess documentFlowProcess;
    private String newTitle = "new title";
    private String newContent = "new content";


    @Before
    public void setUp() throws Exception {
        documentFlowProcess = new DocumentFlowProcess(anyDocumentRepository,
                anyUserManager,
                anyDocumentNumberGenerator,
                anyDocumentFactory,
                anyEmployeeRepository,
                anyEmployeeFactory);
    }

    @Test
    public void shouldCreateDocument() throws Exception {
        //given
        when(anyDocumentNumberGenerator.generate()).thenReturn(anyDocumentNumber);
        when(anyUserManager.currentEmployee()).thenReturn(anyEmployee);
        when(anyDocumentFactory.create(anyDocumentNumber, anyTitle, anyContent, anyEmployee)).thenReturn(anyDocument);

        //when
        DocumentNumber documentNumber = documentFlowProcess.create(anyTitle, anyContent);

        //then
        Mockito.verify(anyDocumentRepository).save(anyDocument);
        assertNotNull(documentNumber);
    }

    @Test
    public void shouldChangeDocument() throws Exception {
        //given
        Document document = new Document(anyDocumentNumber, anyContent, anyTitle, anyEmployee);
        when(anyDocumentRepository.load(anyDocumentNumber)).thenReturn(document);

        //when
        documentFlowProcess.change(anyDocumentNumber, newTitle, newContent);

        //then
        Mockito.verify(anyDocumentRepository).save(document);
        assertEquals(newTitle, document.getTitle());
        assertEquals(newContent, document.getContent());
    }

    @Test
    public void verify() throws Exception {
        //given
        Document document = new Document(anyDocumentNumber, anyContent, anyTitle, anyEmployee);
        when(anyDocumentRepository.load(anyDocumentNumber)).thenReturn(document);
        when(anyUserManager.currentEmployee()).thenReturn(anyEmployee);

        //when
        documentFlowProcess.verify(anyDocumentNumber);

        //then
        Mockito.verify(anyDocumentRepository).save(document);
        assertEquals(anyEmployee, document.getVerificator());
        assertEquals(DocumentStatus.VERIFIED, document.getStatus());
    }

    @Test
    public void publish() throws Exception {
        //given
        Set<EmployeeId> targetEmployees = new HashSet<>(Arrays.asList(anyEmployeeId, anyEmployeeId, anyEmployeeId));
        when(anyDocumentRepository.load(anyDocumentNumber)).thenReturn(anyDocument);
        when(anyUserManager.currentEmployee()).thenReturn(anyEmployee);
        when(anyEmployeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(anyEmployee);
        when(anyEmployeeFactory.create(anyEmployeeId)).thenReturn(anyEmployee);


        //when
        documentFlowProcess.publish(anyDocumentNumber, targetEmployees);

        //then
        //Mockito.verify(anyDocumentRepository).save(document);
        //assertEquals(anyEmployee, document.getPublisher());
        //assertEquals(DocumentStatus.PUBLISHED, document.getStatus());
    }


    @Test
    public void shouldConfirmDocumentByOneEmployee(){

    }









    @Test
    public void archive() throws Exception {
        //given
        Document document = new Document(anyDocumentNumber, anyContent, anyTitle, anyEmployee);
        when(anyDocumentRepository.load(anyDocumentNumber)).thenReturn(document);
        when(anyUserManager.currentEmployee()).thenReturn(anyEmployee);

        //when
        documentFlowProcess.delete(anyDocumentNumber);

        //then
        Mockito.verify(anyDocumentRepository).save(document);
        assertEquals(anyEmployee, document.getDeleter());
        assertTrue(document.isDeleted());


    }

    @Test
    public void createNewVersion() throws Exception {
        //given

        //when

        //then

    }
}