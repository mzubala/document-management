package pl.com.bottega.documentmanagement.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.validator.ValidateWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;


/**
 * Created by anna on 31.07.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class DocumentTest {

    private static final long EPS = 2L * 1000L;
    @Mock
    private DocumentNumber anyNumber;

    @Mock
    private Employee anyEmployee;

    private String anyContent = "test content";

    private String anyTitle = "test title";


    @Test
    public void shouldCreateDocumentWithInitialState() {
        //given
        DocumentNumber number = mock(DocumentNumber.class);
        String content = "test content";
        String title = "test title";
        Employee employee = mock(Employee.class);

        //when
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee);

        //then
        assertEquals(anyNumber, document.number());
        assertEquals(anyContent, document.content());
        assertEquals(anyTitle, document.title());
        assertEquals(anyEmployee, document.creator());
        assertFalse(document.deleted());
        assertEquals(DocumentStatus.DRAFT, document.status());
    }

    @Test
    public void shouldVerifyDocument() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee);
        //when
        document.verify(anyEmployee);

        //then
        assertTrue(new Date().getTime() - document.verifiedAt().getTime() < EPS);
        assertEquals(DocumentStatus.VERIFIED, document.status());
        assertEquals(anyEmployee, document.verificator());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRequireVerificator() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee);

        //when
        document.verify(null);
    }

    @Test
    public void shouldRequireVerificatorOtherWay() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee);

        //when
        try {
            document.verify(null);
        }
        catch(IllegalArgumentException ex) {
            return;
        }
        fail("IllegalArgumentException expected");
    }
}
