package pl.com.bottega.documentmanagement.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by maciuch on 31.07.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class DocumentTest {

    private static Long EPS = 2L * 1000L;
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Mock
    private DocumentNumber anyNumber;
    @Mock
    private Employee anyEmployee;
    @Mock
    private Employee otherEmployee;
    @Mock
    private EmployeeId anyEmployeeId;
    @Mock
    private Reader anyReader;
    private Document document;
    private String anyContent = "test getContent";
    private String anyTitle = "test getTitle";
    private String newContent = "new content";
    private String newTitle = "new title";

    @Before
    public void setUp() {
        document = new Document(anyNumber, anyContent, anyTitle, anyEmployee);
        document.setReaders(new HashSet<>(Arrays.asList(anyReader)));
    }

    @Test
    public void shouldCreateDocumentWithInitialState() {
        assertEquals(1, document.getReaders().size());
        assertEquals(anyNumber, document.getNumber());
        assertEquals(anyContent, document.getContent());
        assertEquals(anyTitle, document.getTitle());
        assertEquals(anyEmployee, document.getCreator());
        assertFalse(document.isDeleted());
        assertEquals(DocumentStatus.DRAFT, document.getStatus());
    }

    @Test
    public void shouldVerifyDocument() {
        document.verify(anyEmployee);

        assertTrue(Math.abs(new Date().getTime() - document.getVerifiedAt().getTime()) < EPS);
        assertEquals(DocumentStatus.VERIFIED, document.getStatus());
        assertEquals(anyEmployee, document.getVerificator());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRequireVerificator() {
        document.verify(null);
    }

    @Test
    public void shouldRequireVerificatorOtherWay() {
        thrown.expect(IllegalArgumentException.class);
        document.verify(null);
    }

    @Test
    public void shouldChangeDocument() {
        document.change(newTitle, newContent);

        assertEquals(newTitle, document.getTitle());
        assertEquals(newContent, document.getContent());
    }

    @Test
    public void shouldDeleteDocument() {
        document.delete(anyEmployee);

        assertTrue(document.isDeleted());
    }

    @Test
    public void shouldPublishDocument() {
        //given
        document.setReaders(new HashSet<>());
        Set<Employee> targetEmployees = new HashSet<>();
        targetEmployees.add(new Employee(anyEmployeeId));

        //when
        document.publish(anyEmployee, targetEmployees);

        //then
        assertEquals(DocumentStatus.PUBLISHED, document.getStatus());
        assertEquals(anyEmployee, document.getPublisher());
        assertNotNull(document.getPublishedAt());
        assertEquals(1, document.getReaders().size());
    }

    @Test
    public void shouldConfirmForSelf() {
        //given
        when(anyReader.getDocument()).thenReturn(document);
        when(anyReader.getTargetEmployee()).thenReturn(anyEmployee);
        when(anyReader.isConfirmed()).thenReturn(false);

        //when
        document.confirm(anyEmployee);

        verify(anyReader).isConfirmed();
        verify(anyReader).confirm(anyEmployee);
    }

    @Test
    public void shouldThrowISEwhenReaderAlreadyConfirmed() {
        //given
        when(anyReader.getDocument()).thenReturn(document);
        when(anyReader.getTargetEmployee()).thenReturn(anyEmployee);
        when(anyReader.isConfirmed()).thenReturn(true);

        //when
        thrown.expect(IllegalArgumentException.class);
        document.confirm(anyEmployee);

        //then
        verify(anyReader).confirm(anyEmployee);
    }

    @Test
    public void shouldConfirmDocumentForOtherEmployee() {
        //given
        when(anyReader.getDocument()).thenReturn(document);
        when(anyReader.getTargetEmployee()).thenReturn(anyEmployee);
        when(anyReader.isConfirmed()).thenReturn(false);
        when(anyEmployee.getEmployeeId()).thenReturn(anyEmployeeId);

        //when
        document.confirm(anyEmployee, otherEmployee);

        //then
        verify(anyReader).confirm(otherEmployee);
    }

    @Test
    public void shouldConfirmDocumentForOtherNullEmployee() {
        //given
        when(anyReader.getDocument()).thenReturn(document);
        when(anyReader.getTargetEmployee()).thenReturn(anyEmployee);
        when(anyReader.isConfirmed()).thenReturn(false);
        when(anyEmployee.getEmployeeId()).thenReturn(anyEmployeeId);

        //when
        document.confirm(anyEmployee, null);

        //then
        verify(anyReader).confirm(anyEmployee);
    }

    @Test
    public void shouldThrowISEwhenReaderForEmployeeNotExists() {
        //given
        when(anyReader.getDocument()).thenReturn(document);
        when(anyReader.getTargetEmployee()).thenReturn(otherEmployee);

        //when
        thrown.expect(IllegalArgumentException.class);
        document.confirm(anyEmployee);
    }

}
