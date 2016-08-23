package pl.com.bottega.documentmanagement.api;


import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.documentmanagement.domain.*;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by bartosz.paszkowski on 11.08.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class DocumentFlowProcessTest {

    private long EPS = 2L*1000L;
    private DocumentFlowProcess documentFlowProcess;
    private String anyContent = "test content";
    private String anyTitle = "test title";
    private String newTitle = "new title";
    private String newContent = "new Content";
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
    @Mock
    private MailingFacade mailingFacade;
    @Mock
    private PrintSystemFacade printSystemFacade;
    @Mock
    private HRSystemFacade hrSystemFacade;

    @Before
    public void setUp(){
        documentFlowProcess = new DocumentFlowProcess(documentNumberGenerator,documentRepository,userManager,documentFactory, employeeRepository,
                hrSystemFacade, printSystemFacade, mailingFacade);
    }

    @Test
    public void shouldCreateDocument(){
        //given

        when(documentFactory.create(anyTitle, anyContent)).thenReturn(document);
        when(document.number()).thenReturn(documentNumber);

        //when
        DocumentNumber result = documentFlowProcess.create(anyTitle,anyContent);
        //documentFlowProcess.verify(result);

        //then
        verify(documentRepository).save(document);
        assertEquals(result, documentNumber);
        assertNotNull(result);
//        assertEquals(anyTitle, document.title());
//        assertEquals(anyContent, document.content());
//        assertEquals(employee, document.creator());
//        assertEquals(documentNumber, document.number());
//        assertEquals(DRAFT, document.status());
    }

    @Test
    public void shouldChangeDocument(){
        //given
        when(documentRepository.load(documentNumber)).thenReturn(document);

        //when
        documentFlowProcess.change(documentNumber, newTitle, newContent);

        //then
        verify(document).change(newTitle, newContent);
        verify(documentRepository).save(document);
//        assertEquals(newTitle, document.title());
//        assertEquals(newContent, document.content());
    }
    @Test
    public void shouldVerifyDocument() {
        //given
        when(documentRepository.load(documentNumber)).thenReturn(document);
        when(userManager.currentEmployee()).thenReturn(employee);

        //when
        documentFlowProcess.verify(documentNumber);

        //then
        verify(document).verify(employee);
        verify(documentRepository).save(document);
    }
    @Test
    public void shouldPublishDocument() {
        //given
        when(documentRepository.load(documentNumber)).thenReturn(document);
        Set<EmployeeId> employeeIds = Sets.newHashSet(1L, 2L, 3L).stream().map(EmployeeId::new).collect(Collectors.toSet());
        when(employeeRepository.findByEmployeeIds(employeeIds)).thenReturn(Sets.newHashSet());
        when(userManager.currentEmployee()).thenReturn(employee);

        //when
        documentFlowProcess.publish(documentNumber, employeeIds);

        //then
        verify(document).publish(employee, employeeIds.stream().map(Employee::new).collect(Collectors.toSet()));
    }
    @Test
    public void shouldArchiveDocument() {
        //given
        when(documentRepository.load(documentNumber)).thenReturn(document);
        when(userManager.currentEmployee()).thenReturn(employee);

        //when
        documentFlowProcess.archive(documentNumber);

        //then
        verify(document).archive(employee);
        verify(documentRepository).save(document);
    }
    @Test
    public void shouldSendPrintRequestForEmployeeWithoutEmail(){

    }

    @Test
    public void shouldSendPublicationEmailsForEmployeeWithEmail(){

    }

}
