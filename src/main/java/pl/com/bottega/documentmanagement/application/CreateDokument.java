package pl.com.bottega.documentmanagement.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.api.UserManager;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

/**
 * Created by bartosz.paszkowski on 18.06.2016.
 */
public class CreateDokument {

    public static void main(String[] args) {
        //przekazanie kontroli springowi nad tworzeniem kontenera
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"application.xml"});
        UserManager userManager = applicationContext.getBean("userManager", UserManager.class);
        userManager.signup("mietek", "12345", new EmployeeId(111L));
        userManager.login("mietek", "12345");
        DocumentFlowProcess documentFlowProcess = applicationContext.getBean("documentFlowProcess", DocumentFlowProcess.class);
        DocumentNumber documentNumber= documentFlowProcess.create("my first doc", "trala la");
        System.out.println(documentNumber);
    }
}
