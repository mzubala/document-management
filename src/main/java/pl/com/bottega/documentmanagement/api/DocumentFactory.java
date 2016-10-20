package pl.com.bottega.documentmanagement.api;

import org.springframework.stereotype.Service;
import pl.com.bottega.documentmanagement.domain.*;

/**
 * Created by bartosz.paszkowski on 17.08.2016.
 */
@Service
public class DocumentFactory {

    private DocumentNumberGenerator documentNumberGenerator;

    private PrintCostCalculator printCostCalculator;

    private UserManager userManager;

    private DocumentListenerManager documentListenerManager;

    public DocumentFactory(DocumentNumberGenerator documentNumberGenerator, UserManager userManager,
                           PrintCostCalculator printCostCalculator, DocumentListenerManager documentListenerManager){
        this.documentNumberGenerator = documentNumberGenerator;
        this.userManager = userManager;
        this.printCostCalculator = printCostCalculator;
        this.documentListenerManager = documentListenerManager;
    }

    public Document create(String title, String content){
        Document document = new Document(documentNumberGenerator.generate(), content, title,
                userManager.currentEmployee(), printCostCalculator);
        documentListenerManager.subscibeListeners(document);
        return document;
    }
}
