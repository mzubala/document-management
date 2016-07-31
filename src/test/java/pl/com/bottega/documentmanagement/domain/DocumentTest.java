package pl.com.bottega.documentmanagement.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by maciuch on 31.07.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class DocumentTest {

    @Mock
    private DocumentNumber anyNumber;

    @Mock
    private Employee anyEmployee;

    private String anyContent = "test content";

    private String anyTitle = "test title";

    private static Long EPS = 2L * 1000L;

    @Test
    public void shouldCreateDocumentWithInitialState() {
        // given

        // when
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee);

        // then
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
        assertTrue(Math.abs(new Date().getTime() - document.verifiedAt().getTime()) < EPS);
        assertEquals(DocumentStatus.VERIFIED, document.status());
        assertEquals(anyEmployee, document.verificator());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRequireVerificator() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee);

        // when
        document.verify(null);

    }

    @Test
    public void shouldRequireVerificatorOtherWay() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee);

        // when
        try {
            document.verify(null);
        }
        catch(IllegalArgumentException ex) {
            return;
        }
        fail("IllegalArgumentException expected");
    }

}
