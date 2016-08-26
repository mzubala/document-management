package pl.com.bottega.documentmanagement.acceptance;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.api.*;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

import javax.persistence.NoResultException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static pl.com.bottega.documentmanagement.domain.DocumentStatus.PUBLISHED;
import static pl.com.bottega.documentmanagement.domain.DocumentStatus.VERIFIED;

/**
 * Created by Dell on 2016-08-20.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration({"/application.xml"})
@TestPropertySource({"/jdbc-test.properties", "/hibernate-test.properties"})
@WebAppConfiguration
public class DocumentFlowAcceptanceTest {

    @Autowired
    private UserManager userManager;

    @Autowired
    private DocumentFlowProcess documentFlowProcess;

    @Autowired
    private DocumentsCatalog documentsCatalog;

    @Autowired
    private ReadingConfirmator readingConfirmator;

    private String anyLogin = "xxx";
    private String anyPassword = "yyy";
    private EmployeeId anyEmployeeId = new EmployeeId(1L);
    private String anyTitle = "title";
    private String anyContent = "content";
    private Set<EmployeeId> readers = new HashSet<>(Arrays.asList(anyEmployeeId, new EmployeeId(10L), new EmployeeId(11L)));


    @Before
    public void setUp() {
        userManager.createAdmin();
    }

    @Test
    @Transactional
    public void shouldCreateDocument() {
        loginAs("EDITOR");

        DocumentNumber nr = documentFlowProcess.create(anyTitle, anyContent);

        DocumentDto dto = documentsCatalog.get(nr);
        assertEquals(anyContent, dto.getContent());
        assertEquals(anyTitle, dto.getTitle());
        assertEquals(nr.getNumber(), dto.getNumber());
    }

    @Test
    @Transactional
    public void shouldVerifyDocument() {
        DocumentNumber nr = createDocument();

        documentFlowProcess.verify(nr);

        DocumentDto dto = documentsCatalog.get(nr);
        assertEquals(VERIFIED.toString(), dto.getStatus());
    }

    @Test
    @Transactional
    public void shouldPublishDocument() {
        DocumentNumber nr = createDocument();
        //todo mock method sending emails
        //todo mock method printing documents

        documentFlowProcess.publish(nr, readers);

        DocumentDto dto = documentsCatalog.get(nr);
        assertEquals(PUBLISHED.toString(), dto.getStatus());
    }

    @Test(expected = NoResultException.class)
    @Transactional
    public void shouldNotGetArchivedDocument() {
        DocumentNumber nr = createDocument();
        documentFlowProcess.archive(nr);

        DocumentDto dto = documentsCatalog.get(nr);
    }

    @Test
    @Transactional
    public void shouldConfirmDocumentHasBeenRead() {
        DocumentNumber nr = createDocument();
        documentFlowProcess.publish(nr, readers);

        readingConfirmator.confirm(nr);

//        Reader reader = ; // TODO: 2016-08-26
    }

    @Test
    @Transactional
    public void shouldConfirmDocumentByOtherEmployee() {
        DocumentNumber nr = createDocument();
        documentFlowProcess.publish(nr, readers);

        readingConfirmator.confirm(nr, new EmployeeId(10L));

//        Reader reader = ; // TODO: 2016-08-26
    }

    @Test
    public void shouldSignUp() {
        SignupResultDto result = userManager.signup(anyLogin, anyPassword, anyEmployeeId);

        assertTrue(result.isSuccess());
        SignupResultDto shouldLogin = userManager.login(anyLogin, anyPassword);
        assertTrue(shouldLogin.isSuccess());

    }

    private DocumentNumber createDocument() {
        loginAs("STAFF", "EDITOR", "MANAGER");
        //todo mock method sending emails
        //todo mock method printing documents
        return documentFlowProcess.create(anyTitle, anyContent);
    }

    private void loginAs(String... roles) {
        userManager.signup(anyLogin, anyPassword, anyEmployeeId);
        userManager.login("admin", "admin");
        userManager.updateRoles(anyEmployeeId, Sets.newHashSet(roles));
        userManager.login(anyLogin, anyPassword);
    }

}
