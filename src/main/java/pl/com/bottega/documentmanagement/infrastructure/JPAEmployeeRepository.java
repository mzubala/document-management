package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Repository;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by bartosz.paszkowski on 19.06.2016.
 */
@Repository
public class JPAEmployeeRepository implements EmployeeRepository{

    @PersistenceContext
    private EntityManager entityManager;


   //public JPAEmployeeRepository(EntityManager entityManager){
    //    this.entityManager = entityManager;
    //}


    @Override
    public void save(Employee employee) {
        entityManager.merge(employee);
    }

    @Override
    public Employee findByEmployeeId(EmployeeId employeeId) {
        return entityManager.find(Employee.class,employeeId);
    }

    @Override
    public boolean isLoginOccupied(String login) {
        return entityManager.
                createQuery("SELECT count (e) " +
                        "from Employee e " +
                        "where login=:login",
                        Long.class).setParameter("login",login).getSingleResult()>0;
    }

    @Override
    public Employee findByLoginAndPassword(String login, String hashedPassword) {
        return entityManager.
                createQuery("SELECT e " +
                                "from Employee e " +
                                "where login=:login and hashedPassword=:hashedPassword",
                        Employee.class).
                setParameter("login",login).
                setParameter("hashedPassword",hashedPassword).getSingleResult();
    }
}
