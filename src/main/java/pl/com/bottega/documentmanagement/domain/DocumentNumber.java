package pl.com.bottega.documentmanagement.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by maciuch on 12.06.16.
 */
@Embeddable
public class DocumentNumber implements Serializable{

    @NaturalId
    private String number;

    public DocumentNumber() {}

    public DocumentNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
