package pl.com.bottega.documentmanagement.api;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.documentmanagement.api.DocumentFactory;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.api.UserManager;
import pl.com.bottega.documentmanagement.domain.*;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static pl.com.bottega.documentmanagement.domain.DocumentStatus.DRAFT;
import static pl.com.bottega.documentmanagement.domain.DocumentStatus.PUBLISHED;
import static pl.com.bottega.documentmanagement.domain.DocumentStatus.VERIFIED;

/**
 * Created by Dell on 2016-08-16.
 */
@RunWith(MockitoJUnitRunner.class)
public class DocumentFlowProcessTest {

    private final static Long EPS = 2L * 1000L;
    private final static Employee EMPLOYEE = new Employee(new EmployeeId(10L));

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

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private Set<EmployeeId> ids = new HashSet<>();

    @Mock
    private EmployeeId employeeId;

    @Mock
    private PrintingCostCalculator printingCostCalculator;

    @Mock
    private HRSystemFasade hrSystemFasade;

    @Mock
    private MailingFasade mailingFasade;

    @Mock
    private PrintSystemFasade printSystemFasade;

    @Before
    public void setUp() {
        documentFlowProcess = new DocumentFlowProcess(documentRepository, userManager, documentFactory, employeeRepository, hrSystemFasade, mailingFasade, printSystemFasade);
    }

    @Test
    public void shouldCreateDocument() {
        //given
        when(documentFactory.create(anyContent, anyTitle)).thenReturn(document);
        when(document.getNumber()).thenReturn(documentNumber);

        DocumentNumber nr = documentFlowProcess.create(anyTitle, anyContent);

        verify(documentRepository).save(document);
        assertEquals(nr, documentNumber);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRequireTitleForCreatingDocument() {
            documentFlowProcess.create(null, anyContent);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRequireContentForCreatingDocument() {
            documentFlowProcess.create(anyTitle, null);
    }

    @Test
    public void shouldUpdateDocument() {
        when(documentRepository.load(documentNumber)).thenReturn(document);

        documentFlowProcess.change(documentNumber, anyTitle, anyContent);

        verify(document).change(anyTitle, anyContent);
        verify(documentRepository).save(document);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRequireDocumentNumberForChangingDocuments() {
            documentFlowProcess.change(null, newTitle, newContent);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRequireTitleForChangingDocuments() {
            documentFlowProcess.change(documentNumber, null, newContent);
    }

    @Test(expected = NullPointerException.class)
    public void shouldRequireContentForChangingDocuments() {
            documentFlowProcess.change(documentNumber, newTitle, null);
    }

    @Test
    public void shouldVerifyDocument() {
        when(documentRepository.load(documentNumber)).thenReturn(document);
        when(userManager.currentEmployee()).thenReturn(employee);

        documentFlowProcess.verify(documentNumber);

        verify(document).verify(employee);
        verify(documentRepository).save(document);

    }

    @Test(expected = NullPointerException.class)
    public void shouldRequireDocumentNumberForVerifyingDocuments() {
            documentFlowProcess.verify(null);
    }

    @Test
    public void shouldArchiveDocument() {
        when(documentRepository.load(documentNumber)).thenReturn(document);
        when(userManager.currentEmployee()).thenReturn(employee);

        documentFlowProcess.archive(documentNumber);

        verify(document).delete(employee);
        verify(documentRepository).save(document);

    }


    @Test(expected = NullPointerException.class)
    public void shouldRequireDocumentNumberForDocumentArchive() {
            documentFlowProcess.archive(null);
    }

    @Test
    public void shouldPublishDocument() {
        when(documentRepository.load(documentNumber)).thenReturn(document);
        Set<EmployeeId> employeeIds = Sets.newHashSet(1L, 2L, 3L).stream().map(EmployeeId::new).collect(Collectors.toSet());
        when(employeeRepository.findByEmployeeIds(employeeIds)).thenReturn(Sets.newHashSet());
        when(userManager.currentEmployee()).thenReturn(employee);

        documentFlowProcess.publish(documentNumber, employeeIds);

        verify(document).publish(employee, employeeIds.stream().map(Employee::new).collect(Collectors.toSet()));
    }

    @Test
    public void shouldSendPrintRequestForEmployeesWithoutEmail() {

    }

    @Test
    public void shouldSendPublicationEmailsToEmployeeWithEmail() {

    }

//    @Test
    public void shouldPublishDocumentWithDigitalExcludedEmployeeCreating() {
        Document document = new Document(new DocumentNumber("test number"), anyContent, anyTitle, employee, printingCostCalculator);
        Set<Long> ids = new LinkedHashSet<>(Arrays.asList(1L, 8L));
        when(documentRepository.load(documentNumber)).thenReturn(document);
        when(userManager.currentEmployee()).thenReturn(employee);

//        documentFlowProcess.publish(documentNumber, ids);

        verify(documentRepository).save(document);
        assertEquals(PUBLISHED, document.getDocumentStatus());
        assertNotNull(document.getReaders());
        assertEquals(2, document.getReaders().size());
    }

    @Test(expected = NullPointerException.class)
    public void shouldRequireEmployeeForPublish() {
            documentFlowProcess.publish(null, ids);
    }
}
