package pl.com.bottega.documentmanagement.domain;

import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import java.io.Serializable;

/**
 * Created by maciuch on 12.06.16.
 */
@Embeddable// inne encje moga jej urzywać
public class EmployeeId implements Serializable{


    @GeneratedValue
    private Long id;

    private EmployeeId(){}

    public EmployeeId (Long id){
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EmployeeId that = (EmployeeId) o;

        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
