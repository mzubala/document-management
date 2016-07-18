package pl.com.bottega.documentmanagement.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

/**
 * Created by Dell on 2016-07-18.
 */
@Entity
public class Role {

    @Id
    @GeneratedValue
    private Long id;
    @NaturalId
    private String name;

    private Role(){}

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
