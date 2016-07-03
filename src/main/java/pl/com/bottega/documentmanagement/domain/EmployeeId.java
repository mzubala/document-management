package pl.com.bottega.documentmanagement.domain;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by Wojciech Winiarski on 12.06.16.
 */
@Embeddable  //klasa moze byc czescia innych encji
public class EmployeeId implements Serializable{


    @GeneratedValue
    private Long id;

    private EmployeeId(){

    }

    public EmployeeId(Long id) {
        this.id = id;

    }
}
