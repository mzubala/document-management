package pl.com.bottega.documentmanagement.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.GeneratedValue;

/**
 * Created by paulina.pislewicz on 2016-07-13.
 */
public class Role {
    @GeneratedValue
    private Long id;
    @NaturalId
    String name;
}
