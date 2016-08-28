package pl.com.bottega.documentmanagement.domain.events;

import pl.com.bottega.documentmanagement.domain.Document;

/**
 * Created by Dell on 2016-08-27.
 */
public interface DocumentListener {

    void published(Document document); //dokument nośnikiem wszystkich potrzebnych informacji, nie potrzeba dodatowego listenera


}
