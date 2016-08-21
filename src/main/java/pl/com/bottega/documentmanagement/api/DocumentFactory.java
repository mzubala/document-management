package pl.com.bottega.documentmanagement.api;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumberGenerator;
import pl.com.bottega.documentmanagement.domain.PrintCostCalculator;


/**
 * Created by maciuch on 17.08.16.
 */
@Component
public class DocumentFactory {

    private DocumentNumberGenerator documentNumberGenerator;

    private PrintCostCalculator printCostCalculator;

    private UserManager userManager;

    public DocumentFactory(DocumentNumberGenerator documentNumberGenerator, UserManager userManager, PrintCostCalculator printCostCalculator) {
        this.documentNumberGenerator = documentNumberGenerator;
        this.userManager = userManager;
        this.printCostCalculator = printCostCalculator;
    }

    public Document create(String title, String content) {
        return new Document(documentNumberGenerator.generate(), content, title, userManager.currentEmployee(), printCostCalculator);
    }

}
