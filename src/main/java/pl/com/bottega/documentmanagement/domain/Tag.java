package pl.com.bottega.documentmanagement.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by paulina.pislewicz on 2016-07-30.
 */
@Entity
public class Tag {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Tag() {
    }

    public Tag (String name){
        this.name = name;

    }
}

