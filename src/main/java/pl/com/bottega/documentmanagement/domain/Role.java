package pl.com.bottega.documentmanagement.domain;

import com.google.common.base.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equal(name, role.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
