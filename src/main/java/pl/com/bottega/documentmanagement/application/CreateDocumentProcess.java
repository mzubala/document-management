package pl.com.bottega.documentmanagement.application;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.api.SignupResultDto;
import pl.com.bottega.documentmanagement.api.UserManager;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;


/**
 * Created by Wojciech Winiarski on 18.06.2016.
 */
public class CreateDocumentProcess {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {"application.xml"});

        UserManager userManager = applicationContext.getBean("userManager", UserManager.class);
        userManager.signup("czesiek", "123", new EmployeeId(45));
        SignupResultDto login = userManager.login("czesiek", "123");
        System.out.println(login);
        DocumentFlowProcess documentFlowProcess = applicationContext.getBean("documentFlowProcess", DocumentFlowProcess.class);
       // documentFlowProcess.create("my first doc", "tresc");
       // DocumentNumber number = documentFlowProcess.create("my 1 document","doc1");
       // System.out.println(number);
        DocumentNumber documentNumber = new DocumentNumber("ISO-c4baedaa-fd0b-4580-b329-c8063646ca0f");
        documentFlowProcess.change(documentNumber, "new document2","new content2");
        System.out.println(documentNumber);




    }
}
