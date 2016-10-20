package pl.com.bottega.documentmanagement.application;

import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumber;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.infrastructure.ColorPrintCostCalculator;
import pl.com.bottega.documentmanagement.infrastructure.ConsoleDocumentBuilder;
import pl.com.bottega.documentmanagement.infrastructure.XMLDocumentBuilder;

/**
 * Created by bartosz.paszkowski on 28.08.2016.
 */
public class DocumentBuilderTestApp {

    public static void main(String[] args) {
        DocumentNumber number = new DocumentNumber("1");

        Document document = new Document(
                number,
                "test content",
                "test title",
                new Employee(new EmployeeId(12L)),
                new ColorPrintCostCalculator());

        XMLDocumentBuilder xmlDocumentBulder = new XMLDocumentBuilder();
        document.export(xmlDocumentBulder);
        ConsoleDocumentBuilder consoleDocumentBuilder = new ConsoleDocumentBuilder();
        document.export(consoleDocumentBuilder);


        System.out.println(xmlDocumentBulder.xml());

    }

}
