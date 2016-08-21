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
import pl.com.bottega.documentmanagement.api.DocumentDto;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.api.DocumentsCatalog;
import pl.com.bottega.documentmanagement.api.UserManager;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.DocumentStatus;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

import static org.junit.Assert.*;

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

    private String anyLogin = "xxx";
    private String anyPassword = "yyy";
    private EmployeeId anyEmployeeId = new EmployeeId(1L);
    private String anyTitle = "title";
    private String anyContent = "content";


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
        loginAs("STAFF", "EDITOR", "MANAGER");
        DocumentNumber nr = documentFlowProcess.create(anyTitle, anyContent);

        documentFlowProcess.verify(nr);

        DocumentDto dto = documentsCatalog.get(nr);
        assertEquals(DocumentStatus.VERIFIED.toString(), dto.getStatus());
    }

    private void loginAs(String... roles) {
        userManager.signup(anyLogin, anyPassword, anyEmployeeId);
        userManager.login("admin", "admin");
        userManager.updateRoles(anyEmployeeId, Sets.newHashSet(roles));
        userManager.login(anyLogin, anyPassword);
    }

}
