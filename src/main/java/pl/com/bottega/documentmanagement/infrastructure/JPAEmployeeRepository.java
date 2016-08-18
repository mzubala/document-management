package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Repository;
import pl.com.bottega.documentmanagement.domain.*;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by paulina.pislewicz on 2016-06-19.
 */
@Repository
public class JPAEmployeeRepository implements EmployeeRepository {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void save(Employee employee) {
        entityManager.merge(employee);
    }

    @Override
    public Employee findByEmployeeId(EmployeeId employeeId) {
       return entityManager.find(Employee.class, employeeId);
    }


    @Override
    public boolean isLoginOccupied(String login) {
        return entityManager.createQuery("SELECT count(e) " +
                "FROM Employee e "+
                "WHERE login =:login", Long.class).setParameter("login", login).getSingleResult() > 0;
    }

    @Override
    public Employee findByLoginAndPassword(String login, String hashedPassword) {
        List<Employee> employees = entityManager.
        createQuery("SELECT e " +
                "FROM Employee e "+
                "WHERE login =:login AND hashedPassword =:hashedPassword",
                Employee.class).
                setParameter("login", login).
                setParameter("hashedPassword", hashedPassword).
                getResultList();
        if(employees.size() ==0)
            return null;
        else
            return employees.get(0);
    }

    @Override
    public Collection<Role> getRoles(Set<String> roleNames) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        Root<Role> root;
        root = criteriaQuery.from(Role.class);
        criteriaQuery.select(root);
        criteriaQuery.where(root.get(Role_.name).in(roleNames));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List <Employee> findByEmployeeIds(Set<EmployeeId> readersIds) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
        Root <Employee> root;
        root = criteriaQuery.from(Employee.class);
        criteriaQuery.select(root);
        criteriaQuery.where(root.get(Employee_.employeeId).in(readersIds));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

}
