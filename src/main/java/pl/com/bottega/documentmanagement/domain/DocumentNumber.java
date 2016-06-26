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

    public DocumentNumber(String number) {
        this.number = number;
    }
    private DocumentNumber(){}

    public String toString(){
        return number;
    }
}
