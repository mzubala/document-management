package pl.com.bottega.documentmanagement.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by maciuch on 12.06.16.
 */
@Embeddable
public class DocumentNumber implements Serializable {
    private static final long serialVersionUID = 1234L;

    @NaturalId
    private String number;

    public DocumentNumber() {}

    public DocumentNumber(String number) {
        this.number = number;
    }

    public String toString() {
        return number;
    }

}
