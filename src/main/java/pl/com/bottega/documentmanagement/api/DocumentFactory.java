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

    public DocumentFactory(DocumentNumberGenerator documentNumberGenerator, UserManager userManager,
                           PrintCostCalculator printCostCalculator){
        this.documentNumberGenerator = documentNumberGenerator;
        this.userManager = userManager;
        this.printCostCalculator = printCostCalculator;
    }

    public Document create(String title, String content){
        return new Document(documentNumberGenerator.generate(), content, title, userManager.currentEmployee(), printCostCalculator);
    }
}
