package pl.com.bottega.documentmanagement.api;

import org.springframework.stereotype.Component;
import pl.com.bottega.documentmanagement.domain.Document;
import pl.com.bottega.documentmanagement.domain.events.DocumentListener;

import java.util.Collection;

/**
 * Created by anna on 27.08.2016.
 */
public class DocumentListenerManager {

    private final Collection<DocumentListener> listeners;

    public DocumentListenerManager(Collection<DocumentListener> listeners) {
        this.listeners = listeners;
    }

    public void subscribeListeners(Document document) {
        for (DocumentListener dl : listeners)
            document.subscribeDocumentListener(dl);
        //listeners.forEach(document::subscribeDocumentListener); można zamiast powyższej pętli
    }

}
