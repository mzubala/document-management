package pl.com.bottega.documentmanagement.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Seta on 2016-07-21.
 */
@Entity
public class Role {


    @Id
    @GeneratedValue
    private Long id;
    @NaturalId
    private String name;


    public Role(){

    }
    public Role(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Role role = (Role) o;

        return name != null ? name.equals(role.name) : role.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

}
