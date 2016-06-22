package pl.com.bottega.documentmanagement.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Embeddable;

/**
 * Created by maciuch on 12.06.16.
 */
@Embeddable
public class DocumentNumber {

    @NaturalId
    private String number;

    private DocumentNumber() {}

    public DocumentNumber(String number) {
        this.number = number;
    }

    public String toString() {
        return number;
    }

}
