package pl.com.bottega.documentmanagement.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.api.UserManager;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

/**
 * Created by Dell on 2016-06-18.
 */
public class CreateDocument {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {"application.xml"});
        UserManager userManager = applicationContext.getBean("userManager", UserManager.class);
        userManager.signup("mietek", "123456", new EmployeeId(666));
        userManager.login("mietek", "123456");
        DocumentFlowProcess documentFlowProcess = applicationContext.getBean("documentFlowProcess", DocumentFlowProcess.class);
        DocumentNumber number = documentFlowProcess.create("my first doc", "trala la");
        System.out.println(number);
    }
}
