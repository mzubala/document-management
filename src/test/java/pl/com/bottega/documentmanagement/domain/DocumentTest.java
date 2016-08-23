package pl.com.bottega.documentmanagement.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by bartosz.paszkowski on 31.07.2016.
 */

@RunWith(MockitoJUnitRunner.class)
public class DocumentTest {

    @Mock
    private DocumentNumber anyNumber;
    @Mock
    private Employee anyEmployee;
    private String anyContent = "test content";
    private String anyTitle = "test title";
    @Mock
    private Date anyDate;
    private long EPS = 2l*1000l;
    @Mock
    private PrintCostCalculator printCostCalculator;


    @Test
    public void shouldCreateDocumentWithInitialState(){
        //given
        //DocumentNumber number = mock(DocumentNumber.class);
        //String content = "test content";
        //String title = "test title";
        //Employee employee = mock(Employee.class);

        //when
        Document document = new Document(anyNumber,anyContent,anyTitle,anyEmployee,printCostCalculator);

        //then
        assertEquals(anyNumber, document.number());
        assertEquals(anyContent, document.content());
        assertEquals(anyTitle,document.title());
        assertEquals(anyEmployee, document.creator());
        assertFalse(document.deleted());
        assertEquals(DocumentStatus.DRAFT, document.status());
    }

    @Test
    public void shouldVerifyDocument(){
        //given
        Document document = new Document(anyNumber,anyContent,anyTitle,anyEmployee,printCostCalculator);

        //when
        document.verify(anyEmployee);

        //then
        assertEquals(DocumentStatus.VERIFIED, document.status());
        assertEquals(document.verifier(), anyEmployee);
        assertNotNull(document.verificationAt());
        assertTrue(Math.abs(new Date().getTime() - document.verificationAt().getTime())<EPS);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRequiredVerifier(){
        //given
        Document document = new Document(anyNumber,anyContent,anyTitle,anyEmployee,printCostCalculator);

        //when
        document.verify(null);

        //then

    }

    @Test()
    public void shouldRequiredVerifierOtherWay() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee,printCostCalculator);

        //when
        try {
            document.verify(null);
        } catch (IllegalArgumentException ex) {
            return;
        }
        fail("IllegalArgumentException expected");
    }

        //then


    @Test
    public void shouldEditDocument(){
        //given
        Document document = new Document(anyNumber,anyContent,anyTitle,anyEmployee,printCostCalculator);

        //when
        document.change("newTitle","NewContent");

        //then
        assertEquals("newTitle", document.title());
        assertEquals("NewContent", document.content());
    }

    @Test
    public void shouldDeleteDocument(){
        //given
        Document document = new Document(anyNumber,anyContent,anyTitle,anyEmployee,printCostCalculator);

        //when
        document.archive(anyEmployee);

        //then
        assertTrue(document.deleted());


    }
}
        //given


        //when


        //then