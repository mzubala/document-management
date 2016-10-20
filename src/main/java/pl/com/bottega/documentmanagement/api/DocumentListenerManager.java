package pl.com.bottega.documentmanagement.api;

import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.events.DocumentListener;

import java.util.Collection;

/**
 * Created by bartosz.paszkowski on 27.08.2016.
 */
//@Component
public class DocumentListenerManager {

    private final Collection<DocumentListener> listeners;

    public DocumentListenerManager(Collection<DocumentListener> listeners){
        this.listeners = listeners;
    }

    public void subscibeListeners(Document document){
        for (DocumentListener dl : listeners)
            document.subscribeDocumentListener(dl);
        //listeners.forEach(document::subscribeDocumentListener);
    }

}
