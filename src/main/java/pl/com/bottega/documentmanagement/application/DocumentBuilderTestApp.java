package pl.com.bottega.documentmanagement.application;

import pl.com.bottega.documentmanagement.domain.*;
import pl.com.bottega.documentmanagement.infrastructure.ColorPrintCostCalculator;
import pl.com.bottega.documentmanagement.infrastructure.ConsoleDocumentPrinter;
import pl.com.bottega.documentmanagement.infrastructure.XMLDocumentBuilder;

/**
 * Created by maciuch on 28.08.16.
 */
public class DocumentBuilderTestApp {

    public static void main(String[] args) {
        DocumentNumber documentNumber = new DocumentNumber("1");
        Document document = new Document(
                documentNumber,
                "test content",
                "test title",
                new Employee(new EmployeeId(12L)),
                new ColorPrintCostCalculator()
        );

        XMLDocumentBuilder xmlDocumentBuilder = new XMLDocumentBuilder();
        document.export(xmlDocumentBuilder);
        System.out.println(xmlDocumentBuilder.xml());

        ConsoleDocumentPrinter consoleDocumentPrinter = new ConsoleDocumentPrinter();
        document.export(consoleDocumentPrinter);
    }

}
