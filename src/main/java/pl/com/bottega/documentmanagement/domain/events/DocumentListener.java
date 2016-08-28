package pl.com.bottega.documentmanagement.domain.events;

import pl.com.bottega.documentmanagement.domain.Document;

/**
 * Created by maciuch on 27.08.16.
 */
public interface DocumentListener {

    void published(Document document);

}
