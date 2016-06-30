package pl.com.bottega.documentmanagement.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.api.SignupResultDto;
import pl.com.bottega.documentmanagement.api.UserManager;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

/**
 * Created by bartosz.paszkowski on 18.06.2016.
 */
public class CreateDocument {

    public static void main(String[] args) {
        //przekazanie kontroli springowi nad tworzeniem kontenera
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"application.xml"});
        UserManager userManager = applicationContext.getBean("userManager", UserManager.class);
        userManager.signup("czesiek", "qwert", new EmployeeId(333L));
        SignupResultDto result = userManager.login("czesiek", "qwert");
        System.out.println(result);
        DocumentFlowProcess documentFlowProcess = applicationContext.getBean("documentFlowProcess", DocumentFlowProcess.class);
        //DocumentNumber documentNumber= documentFlowProcess.create("my first doc", "trala la");
        //System.out.println(documentNumber);
        //documentFlowProcess.change(documentNumber,"changed title","changed content");
        //System.out.println(documentNumber);
        DocumentNumber documentNumber2 = new DocumentNumber("ISO-1e40809c-508c-4056-8c90-2bc3805002b9");
        documentFlowProcess.change(documentNumber2,"changed title2","changed content2");





    }
}
