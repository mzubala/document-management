package pl.com.bottega.documentmanagement.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by paulina.pislewicz on 2016-07-13.
 */
@Entity
public class Role {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    @NaturalId
    String name;

    public Role (String name){
        this.name = name;
    }

    private Role(){}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        return name.equals(role.name);

    }


}

