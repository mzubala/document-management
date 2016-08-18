package pl.com.bottega.documentmanagement.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.documentmanagement.domain.*;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;

import javax.print.Doc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static pl.com.bottega.documentmanagement.domain.DocumentStatus.DRAFT;

/**
 * Created by Seta on 2016-08-11.
 */
@RunWith(MockitoJUnitRunner.class)
public class DocumentFlowProcessTest {

    private Long EPS = 2L*1000L;
    private DocumentFlowProcess documentFlowProcess;
    private String anyContent = "test content";
    private String anyTitle = "test title";
    private String newTitle = "new title";
    private String newContent = "new content";
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
    @Mock
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp(){
        documentFlowProcess = new DocumentFlowProcess(documentNumberGenerator, documentRepository, userManager,documentFactory,employeeRepository);
    }

    @Test
    public void shouldCreateDocument(){
        //given
        Document document = new Document(documentNumber, anyContent,anyTitle,employee);
        when(documentNumberGenerator.generate()).thenReturn(documentNumber);
        when(userManager.currentEmployee()).thenReturn(employee);
        when(documentFactory.create(documentNumber, anyContent,anyTitle,employee)).thenReturn(document);

        //when
        DocumentNumber result = documentFlowProcess.create(anyTitle,anyContent);

        //then
        assertNotNull(result);
        assertEquals(anyContent, document.content());
        assertEquals(anyTitle, document.title());
        assertEquals(employee, document.creator());
        assertEquals(documentNumber, document.number());
        assertEquals(DRAFT,document.status());
    }

    @Test
    public void shouldChangeDocument(){
        //given
        Document document = new Document(documentNumber, anyContent, anyTitle, employee);
        when(documentRepository.load(documentNumber)).thenReturn(document);
        //when
        documentFlowProcess.change(documentNumber,newTitle,newContent);
        //then
        assertEquals(newContent, document.content());
        assertEquals(newTitle, document.title());

    }





}
