package pl.com.bottega.documentmanagement.infrastructure;

import com.google.common.hash.HashCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.EmployeeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Wojciech Winiarski on 19.06.2016.
 */
@Repository
public class JPAEmployeeRepository implements EmployeeRepository{

    @PersistenceContext
    private EntityManager entityManager;



    @Override
    public void save(Employee employee) {

        entityManager.merge(employee);

    }

    @Override
    public Employee findByEmployee(EmployeeId employeeId) {
       return   entityManager.find(Employee.class, employeeId);
    }

    @Override
    public boolean isLoginOccupied(String login) {
        return entityManager.createQuery("SELECT count(e) " +
                "from Employee e " +
                "where login =:login",
                Long.class).setParameter("login", login).getSingleResult() > 0;
    }

    @Override
    public Employee findByLoginAndPassword(String login, String hashedPassword) {
        List<Employee> employees = entityManager.createQuery("from Employee " +
                        "where login =:login and hashedPassword=:hashedPassword",
                Employee.class).setParameter("login", login)
                .setParameter("hashedPassword", hashedPassword)
                .getResultList();
        if (employees.size() == 0)
            return null;
        else
            return employees.get(0);

    }
}
