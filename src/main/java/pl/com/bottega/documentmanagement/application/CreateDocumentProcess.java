package pl.com.bottega.documentmanagement.application;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
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
        userManager.signup("mietek", "123", new EmployeeId(55));
        userManager.login("mietek", "123");
        DocumentFlowProcess documentFlowProcess = applicationContext.getBean("documentFlowProcess", DocumentFlowProcess.class);
        documentFlowProcess.create("my first doc", "tresc");
        DocumentNumber number = documentFlowProcess.create("my 1 document","doc1");
        System.out.println(number);




    }
}
