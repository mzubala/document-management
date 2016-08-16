package pl.com.bottega.documentmanagement.domain;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Dell on 2016-07-31.
 */
@RunWith(MockitoJUnitRunner.class)
public class DocumentTest {

    @Mock
    private DocumentNumber anyNumber;

    @Mock
    private Employee anyEmployee;

    @Mock
    private Employee anyVerificator;

    private String anyContent = "test content";

    private String anyTitle = "test title";

    private static Long EPS = 2L * 1000L; //epsilon

    @Test
    public void shouldCreateDocumentWithInitialState() {
        //given
        //when
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee);

        //then
        assertEquals(anyNumber, document.getNumber());
        assertEquals(anyContent, document.getContent());
        assertEquals(anyTitle, document.getTitle());
        assertEquals(anyEmployee, document.getCreator());
        assertFalse(document.getDeleted());
        assertEquals(DocumentStatus.DRAFT, document.getDocumentStatus());
    }

    @Test
    public void shouldVerifiedDocument() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee);

        //when
        document.verify(anyVerificator);

        //then
        assertEquals(anyVerificator, document.getVerificator());
        assertEquals(DocumentStatus.VERIFIED, document.getDocumentStatus());
//        assertNotNull(document.getCreateAt());
        assertTrue(Math.abs(new Date().getTime() - document.getVerifiedAt().getTime()) < EPS);
    }

    @Test
    public void shouldRequireVerificator() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee);

        //when
        try {
            document.verify(null);
        }
        catch (IllegalArgumentException ex) {
            return;
        }
        fail("IllegalArgumentException excpected");

        //then

    }
}
