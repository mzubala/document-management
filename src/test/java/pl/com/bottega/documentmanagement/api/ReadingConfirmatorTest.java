package pl.com.bottega.documentmanagement.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.documentmanagement.domain.*;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Dell on 2016-06-12.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReadingConfirmatorTest {

    private final static Long EPS = 2L * 1000L; //epsilon

    private ReadingConfirmator readingConfirmator;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private UserManager userManager;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DocumentNumber anyDocumentNumber;

    @Mock
    private Document anyDocument;

    @Mock
    private Employee anyEmployee;

    @Mock
    private Employee anyConfirmator;

    @Mock
    private Reader anyReader;

    @Mock
    private EmployeeId anyEmployeeId;

    @Before
    public void setUp() {
        readingConfirmator = new ReadingConfirmator(documentRepository, userManager, employeeRepository);
    }

    @Test
    public void shouldConfirmDocument() {
        Document document = new Document(anyDocumentNumber, "", "", anyConfirmator);
        Reader reader = new Reader(document, anyEmployee);
        Set<Employee> readers = new HashSet<>(Arrays.asList(anyEmployee));
        document.publish(anyEmployee, readers);

        when(documentRepository.load(anyDocumentNumber)).thenReturn(document);
        when(userManager.currentEmployee()).thenReturn(anyEmployee);

        readingConfirmator.confirm(anyDocumentNumber);

        verify(documentRepository).save(document);
        assertTrue(reader.isConfirmed());
        assertTrue(Math.abs(new Date().getTime() - reader.getConfirmedAt().getTime()) < EPS);
        assertNull(reader.getConfirmedBy());
        assertEquals(anyEmployee, reader.getEmployee());
    }

    @Test
    public void shouldConfirmDocumentByOtherEmployee() {
        Document document = new Document(anyDocumentNumber, "", "", anyConfirmator);
        Reader reader = new Reader(document, anyEmployee);
        Set<Employee> readers = new HashSet<>(Arrays.asList(anyEmployee));
        document.publish(anyEmployee, readers);

        when(documentRepository.load(anyDocumentNumber)).thenReturn(document);
        when(userManager.currentEmployee()).thenReturn(anyConfirmator);
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(anyEmployee);

        readingConfirmator.confirm(anyDocumentNumber, anyEmployeeId);

        verify(documentRepository).save(document);
        assertTrue(reader.isConfirmed());
        assertTrue(Math.abs(new Date().getTime() - reader.getConfirmedAt().getTime()) < EPS);
        assertEquals(anyConfirmator, reader.getConfirmedBy());
        assertEquals(anyEmployee, reader.getEmployee());
    }

    @Test
    public void shouldNotConfirmDocumentBecauseEmployeeIsNotAReader() {
        Document document = new Document(anyDocumentNumber, "", "", anyConfirmator);
        Set<Employee> readers = new HashSet<>(Arrays.asList(anyEmployee, anyEmployee));
        document.publish(anyEmployee, readers);

        when(documentRepository.load(anyDocumentNumber)).thenReturn(document);
        when(userManager.currentEmployee()).thenReturn(anyEmployee);

        try {
            readingConfirmator.confirm(anyDocumentNumber);
        }
        catch (IllegalArgumentException ex) {
            return;
        }
        fail("IllegalArgumentException expected");
    }

    @Test
    public void shouldNotConfirmDocumentByOtherBecauseEmployeeIsNotAReader() {
        Document document = new Document(anyDocumentNumber, "", "", anyConfirmator);
        Set<Employee> readers = new HashSet<>(Arrays.asList(anyEmployee, anyEmployee));
        document.publish(anyEmployee, readers);

        when(documentRepository.load(anyDocumentNumber)).thenReturn(document);
        when(userManager.currentEmployee()).thenReturn(anyConfirmator);
        when(employeeRepository.findByEmployeeId(anyEmployeeId)).thenReturn(anyEmployee);

        try {
            readingConfirmator.confirm(anyDocumentNumber, anyEmployeeId);
        }
        catch (IllegalArgumentException ex) {
            return;
        }
        fail("IllegalArgumentException expected");
    }

}
