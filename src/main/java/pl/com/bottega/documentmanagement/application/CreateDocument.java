package pl.com.bottega.documentmanagement.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.com.bottega.documentmanagement.api.DocumentFlowProcess;
import pl.com.bottega.documentmanagement.api.SignupResultDto;
import pl.com.bottega.documentmanagement.api.UserManager;
import pl.com.bottega.documentmanagement.domain.*;
import pl.com.bottega.documentmanagement.domain.repositories.DocumentRepository;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;
import pl.com.bottega.documentmanagement.infrastructure.JPADocumentRepository;
import pl.com.bottega.documentmanagement.infrastructure.JPAEmployeeRepository;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by maciuch on 18.06.16.
 */
public class CreateDocument {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"application.xml"});
        /*UserManager userManager = applicationContext.getBean("userManager", UserManager.class);
        userManager.signup("janek2", "qwerty", new EmployeeId(51L));
        SignupResultDto result = userManager.login("janek", "bum");
        System.out.println(result);
        DocumentFlowProcess documentFlowProcess = applicationContext.getBean("documentFlowProcess", DocumentFlowProcess.class);
        DocumentNumber number = documentFlowProcess.create("my first doc", "trala la");
        documentFlowProcess.change(number, "changed getTitle", "changed getContent");
        System.out.println(number);
*/
        EmployeeRepository employeeRepository = applicationContext.getBean(JPAEmployeeRepository.class);
        Employee janek = employeeRepository.findByEmployeeId(new EmployeeId(1L));


        DocumentFlowProcess documentFlowProcess = applicationContext.getBean(DocumentFlowProcess.class);
        DocumentNumber number = documentFlowProcess.create("my first doc", "trala la");


        Set<EmployeeId> employees = new HashSet<>();
        employees.add(new EmployeeId(1L));

        documentFlowProcess.publish(number, employees);
    }

}
