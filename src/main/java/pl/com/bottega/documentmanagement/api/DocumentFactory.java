package pl.com.bottega.documentmanagement.api;

import org.springframework.stereotype.Service;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.DocumentNumberGenerator;

/**
 * Created by Dell on 2016-08-16.
 */
@Service
public class DocumentFactory {

    private DocumentNumberGenerator documentNumberGenerator;
    private UserManager userManager;

    public DocumentFactory(DocumentNumberGenerator documentNumberGenerator, UserManager userManager) {
        this.documentNumberGenerator = documentNumberGenerator;
        this.userManager = userManager;
    }

    public Document create(String content, String title) {
        return new Document(documentNumberGenerator.generate(), content, title, userManager.currentEmployee());
    }

}
