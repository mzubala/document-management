package pl.com.bottega.documentmanagement.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Wojciech Winiarski on 30.07.2016.
 */
@Entity
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    private String  name;
    public Tag(String name){
        this.name = name;
    }
    public Tag(){

    }



}
