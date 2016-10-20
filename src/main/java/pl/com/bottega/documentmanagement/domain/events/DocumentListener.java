package pl.com.bottega.documentmanagement.domain.events;

import pl.com.bottega.documentmanagement.domain.Document;

/**
 * Created by bartosz.paszkowski on 27.08.2016.
 */
public interface DocumentListener {
    void published(Document document);

}
