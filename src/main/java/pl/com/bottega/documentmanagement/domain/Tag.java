package pl.com.bottega.documentmanagement.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by maciuch on 30.07.16.
 */
@Entity
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Tag() {}

    public Tag(String name) {
        this.name = name;
    }

}
