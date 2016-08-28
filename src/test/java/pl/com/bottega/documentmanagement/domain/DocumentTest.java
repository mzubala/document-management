package pl.com.bottega.documentmanagement.domain;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.com.bottega.documentmanagement.domain.events.DocumentListener;

import java.util.Date;
import java.util.Set;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static pl.com.bottega.documentmanagement.utils.Assert.assertDatesEqual;

/**
 * Created by maciuch on 31.07.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class DocumentTest {

    @Mock
    private DocumentNumber anyNumber;

    @Mock
    private Employee anyEmployee;

    @Mock
    private Employee otherEmployee;

    @Mock
    private PrintCostCalculator printCostCalculator;

    private String anyContent = "test content";

    private String anyTitle = "test title";

    private String newTitle = "new title";

    private String newContent = "new content";
    private static Long EPS = 2L * 1000L;

    @Test
    public void shouldCreateDocumentWithInitialState() {
        // given

        // when
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee, printCostCalculator);

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
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee, printCostCalculator);

        //when
        document.verify(anyEmployee);

        //then
        assertDatesEqual(new Date(), document.verifiedAt());
        assertEquals(DocumentStatus.VERIFIED, document.status());
        assertEquals(anyEmployee, document.verificator());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRequireVerificator() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee, printCostCalculator);

        // when
        document.verify(null);

    }

    @Test
    public void shouldRequireVerificatorOtherWay() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee, printCostCalculator);

        // when
        try {
            document.verify(null);
        } catch (IllegalArgumentException ex) {
            return;
        }
        fail("IllegalArgumentException expected");
    }

    @Test
    public void shouldChangeDocument() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee, printCostCalculator);

        //when
        document.change(newTitle, newContent);

        //then
        assertEquals(newTitle, document.title());
        assertEquals(newContent, document.content());
        assertDatesEqual(new Date(), document.updatedAt());
    }

    @Test
    public void shouldChangeStatusToDraftAfterUpdate() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee, printCostCalculator);
        document.verify(anyEmployee);

        //when
        document.change(newTitle, newContent);

        //then
        assertEquals(DocumentStatus.DRAFT, document.status());
    }

    @Test
    public void shouldDeleteDocument() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee, printCostCalculator);

        //when
        document.delete(anyEmployee);

        //then
        assertTrue(document.deleted());
        assertEquals(anyEmployee, document.deletor());
    }

    @Test
    public void shouldPublishDocument() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee, printCostCalculator);
        document.verify(anyEmployee);

        //when
        Set<Employee> readers = Sets.newHashSet(anyEmployee, otherEmployee);
        document.publish(anyEmployee, readers);

        //then
        assertEquals(Sets.newHashSet(new Reader(document, anyEmployee), new Reader(document, otherEmployee)), document.readers());
        document.readers().stream().forEach((reader) -> assertFalse(reader.confirmed()));
        assertEquals(anyEmployee, document.publisher());
        assertDatesEqual(new Date(), document.publishedAt());
    }

    @Test
    public void shouldNotifyAboutPublishing() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee, printCostCalculator);
        document.verify(anyEmployee);
        DocumentListener firstListener = mock(DocumentListener.class);
        DocumentListener secondListener = mock(DocumentListener.class);
        document.subscribeDocumentListener(firstListener);
        document.subscribeDocumentListener(secondListener);

        //when
        document.publish(anyEmployee, Sets.newHashSet(anyEmployee));

        //then
        verify(firstListener).published(document);
        verify(secondListener).published(document);

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowPublishingForNoEmployees() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee, printCostCalculator);

        //when
        document.publish(anyEmployee, Sets.newHashSet());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotAllowPublishingUnverifiedDocument() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee, printCostCalculator);

        //when
        Set<Employee> readers = Sets.newHashSet(anyEmployee, otherEmployee);
        document.publish(anyEmployee, readers);
    }

    @Test
    public void shouldConfirmDocument() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee, printCostCalculator);
        document.verify(anyEmployee);
        Set<Employee> readers = Sets.newHashSet(otherEmployee);
        document.publish(anyEmployee, readers);

        //when
        document.confirm(otherEmployee);

        //then
        Reader reader = document.reader(otherEmployee);
        assertTrue(reader.confirmed());
        assertDatesEqual(new Date(), reader.confirmedAt());
    }

    @Test
    public void shouldConfirmDocumentForOtherEmployee() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee, printCostCalculator);
        document.verify(anyEmployee);
        Set<Employee> readers = Sets.newHashSet(otherEmployee);
        document.publish(anyEmployee, readers);

        //when
        document.confirm(anyEmployee, otherEmployee);

        //then
        Reader reader = document.reader(otherEmployee);
        assertTrue(reader.confirmed());
        assertDatesEqual(new Date(), reader.confirmedAt());
        assertEquals(anyEmployee, reader.confirmedBy());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotConfirmDocumentForNonReaderEmployee() {
        //given
        Document document = new Document(anyNumber, anyContent, anyTitle, anyEmployee, printCostCalculator);
        document.verify(anyEmployee);
        Set<Employee> readers = Sets.newHashSet(anyEmployee);
        document.publish(anyEmployee, readers);

        //when
        document.confirm(otherEmployee);

    }

}
