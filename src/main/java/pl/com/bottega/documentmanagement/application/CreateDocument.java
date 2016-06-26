package pl.com.bottega.documentmanagement.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.api.SignupResultDto;
import pl.com.bottega.documentmanagement.api.UserManager;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

/**
 * Created by paulina.pislewicz on 2016-06-18.
 */
public class CreateDocument {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String [] {"application.xml"});//  param - scieżka do pliku konfiguracji Springa
        UserManager userManager = applicationContext.getBean("userManager", UserManager.class);
        userManager.signup("janek", "qwerty", new EmployeeId(50l));
        SignupResultDto result = userManager.login("mietek", "123456");
        System.out.println(result);
        DocumentFlowProcess documentFlowProcess = applicationContext.getBean("documentFlowProcess", DocumentFlowProcess.class);
        DocumentNumber number = documentFlowProcess.create("my first doc", "trala la");
        System.out.println(number);
        documentFlowProcess.change(number, "new title", "sia la la");
        System.out.println(number);

    }

    @Override
    public String toString() {
        return "CreateDocument{}";
    }

}
