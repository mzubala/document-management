package pl.com.bottega.documentmanagement.acceptance;

import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.com.bottega.documentmanagement.api.DocumentDto;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.api.DocumentsCatalog;
import pl.com.bottega.documentmanagement.api.UserManager;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static pl.com.bottega.documentmanagement.domain.DocumentStatus.VERIFIED;
import static pl.com.bottega.documentmanagement.utils.Assert.assertDatesEqual;

/**
 * Created by bartosz.paszkowski on 23.08.2016.
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
    private DocumentsCatalog documentCatalog;

    private String anyLogin  = "xxx";
    private String anyPassword = "xxx";
    private Long anyId = 1L;
    private EmployeeId anyEmployeeId = new EmployeeId(anyId);
    private String anyTitle = "test";
    private String anyContent = "test content";

    @Before
    public void setUp(){
        userManager.createAdmin();
    }

    @Test
    public void shouldCreateDocument(){
        //given
        loginAs("STAFF", "EDITOR");

        //when
        DocumentNumber number =  documentFlowProcess.create(anyTitle,anyContent);

        //then
        DocumentDto dto = documentCatalog.get(number);
        assertEquals(anyContent, dto.getContent());
        assertEquals(anyTitle, dto.getTitle());
        assertEquals(number.getNumber(), dto.getNumber());
        assertDatesEqual(new Date(), dto.getCreatedAt());
    }

    @Test
    public void shouldVerifyDocument(){
        //given
        loginAs("MANAGER", "STAFF", "EDITOR");
        DocumentNumber number =  documentFlowProcess.create(anyTitle,anyContent);

        //when
        documentFlowProcess.verify(number);

        //then
        DocumentDto dto = documentCatalog.get(number);
        assertEquals(VERIFIED.name(), dto.getStatus());

    }

    private void loginAs(String ...roles){
        userManager.signup(anyLogin, anyPassword, anyEmployeeId);
        userManager.login("admin", "admin");
        userManager.updateRoles(anyEmployeeId, Sets.newHashSet(roles));
        userManager.login(anyLogin,anyPassword);
    }


}
