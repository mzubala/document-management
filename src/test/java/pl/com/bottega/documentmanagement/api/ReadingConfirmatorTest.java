package pl.com.bottega.documentmanagement.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by bartosz.paszkowski on 23.08.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ReadingConfirmatorTest {
    private ReadingConfirmator readingConfirmator;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UserManager userManager;

    @Mock
    private DocumentNumber documentNumber;

    @Mock
    private Document document;

    @Mock
    private Employee employee, otherEmployee;

    @Mock
    private EmployeeId otherEmployeeId;

    @Before
    public void setUp() throws Exception {
        readingConfirmator = new ReadingConfirmator(documentRepository, employeeRepository, userManager);
    }

    @Test
    public void shouldConfirmDocument() {
        //given
        when(documentRepository.load(documentNumber)).thenReturn(document);
        when(userManager.currentEmployee()).thenReturn(employee);

        //when
        readingConfirmator.confirm(documentNumber);

        //then
        verify(document).confirm(employee);
    }

    @Test
    public void shouldConfirmDocumentForOtherEmployee() {
        //given
        when(documentRepository.load(documentNumber)).thenReturn(document);
        when(userManager.currentEmployee()).thenReturn(employee);
        when(employeeRepository.findByEmployeeId(otherEmployeeId)).thenReturn(otherEmployee);

        //when
        readingConfirmator.confirm(documentNumber, otherEmployeeId);

        //then
        verify(document).confirm(employee, otherEmployee);
    }
}
