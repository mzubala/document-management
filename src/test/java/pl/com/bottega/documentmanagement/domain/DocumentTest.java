package pl.com.bottega.documentmanagement.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;

import java.util.Date;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Created by Wojciech Winiarski on 31.07.2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class DocumentTest {
    @Mock
    private DocumentNumber anyNumber;
    @Mock
    private Employee anyEmployee;

    private String anyContent = "Test content";
    private String anyTitle = "Test title";

    @Mock
    private Employee verificator;
    private static long EPS = 2L*1000L;



    @Test
    public void shouldCreateDocumentWithInitialState() {


        //given

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
        assertNotNull(document.verifiedAt());
        assertEquals(DocumentStatus.VERIFIED, document.status());
        assertEquals(anyEmployee, document.veryficator());
        assertTrue(Math.abs(new Date().getTime() - document.verifiedAt().getTime()) < EPS);

    }

    @Test
    public void shouldRequireVerificatorOtherWay() throws IllegalArgumentException {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee);
        //when
        try {
            document.verify(null);
        }catch (IllegalArgumentException ex){
            return;
        }
        fail("Illegal argument exception expected");

    }
    @Test
    public void shouldEditDocument(){

        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee);
        //when
        document.change("newTitle", "newContent");

        //then
        assertEquals("newTitle", document.title());
        assertEquals("newContent", document.content());
    }
    @Test
    public void shouldDeleteDocument(){

        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee);
        //when
        document.archive(anyEmployee);
        //then
        assertTrue(document.deleted());

    }

}
    //given

    //when

    //then
