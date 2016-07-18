package pl.com.bottega.documentmanagement.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.com.bottega.documentmanagement.api.*;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
//import pl.com.bottega.documentmanagement.infrastructure.JPADocumentsCatalog;

/**
 * Created by maciuch on 18.06.16.
 */
public class CreateDocument {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"application.xml"});
        UserManager userManager = applicationContext.getBean("userManager", UserManager.class);
        userManager.signup("jan", "qwerty", new EmployeeId(10L));
        SignupResultDto result = userManager.login("janek5", "qwerty");
        System.out.println(result.toString());
        DocumentFlowProcess documentFlowProcess = applicationContext.getBean("documentFlowProcess", DocumentFlowProcess.class);
        DocumentNumber number = documentFlowProcess.create("my second doc", "exciting content");
        System.out.println(number);

        //uploaduje document i wyświetlam w postaci DTO
//        DocumentsCatalog documentsCatalog = applicationContext.getBean("documentsCatalog", JPADocumentsCatalog.class);
//        DocumentNumber documentNumber = new DocumentNumber("ISO-0717b880-c799-4f17-92b3-9429759f8265");
//        DocumentDto documentDto = documentsCatalog.get(number);
//        System.out.println("number: " + documentDto.getNumber() + "/title: " + documentDto.getTitle() + "/content: " + documentDto.getContent());
//
//        updatedocument
//        documentFlowProcess.change(number, "newTitle", "newContent");
//        documentDto = documentsCatalog.get(number);
//        System.out.println("number: " + documentDto.getNumber() + "/title: " + documentDto.getTitle() + "/content: " + documentDto.getContent());

    }
}
