package pl.com.bottega.documentmanagement.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.com.bottega.documentmanagement.api.*;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.EmployeeId;

/**
 * Created by maciuch on 18.06.16.
 */
public class CreateDocument {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"application.xml"});
        UserManager userManager = applicationContext.getBean("userManager", UserManager.class);
        userManager.signup("janek4", "qwerty", new EmployeeId(5111L));
        SignupResultDto result = userManager.login("zdzisiek", "123456");
        System.out.println(result.toString());
        DocumentFlowProcess documentFlowProcess = applicationContext.getBean("documentFlowProcess", DocumentFlowProcess.class);
        DocumentNumber number = documentFlowProcess.create("my second doc", "exciting content");
        System.out.println(number);

        //uploaduje document i wyświetlam w postaci DTO
        DocumentsCatalog documentsCatalog = applicationContext.getBean("documentsCatalog", DocumentsCatalog.class);
        DocumentNumber documentNumber = new DocumentNumber("ISO-4c2098d9-15c1-478b-8968-1011322049be");
        DocumentDto documentDto = documentsCatalog.get(documentNumber);
        System.out.println("number: " + documentDto.getNumber() + "/title: " + documentDto.getTitle() + "/content: " + documentDto.getContent());

        //updatedocument
        documentFlowProcess.change(documentNumber, "newTitle", "newContent");
        documentDto = documentsCatalog.get(documentNumber);
        System.out.println("number: " + documentDto.getNumber() + "/title: " + documentDto.getTitle() + "/content: " + documentDto.getContent());

    }
}
