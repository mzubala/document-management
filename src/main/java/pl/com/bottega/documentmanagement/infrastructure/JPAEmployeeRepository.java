package pl.com.bottega.documentmanagement.infrastructure;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Repository;
import pl.com.bottega.documentmanagement.domain.Employee;
import pl.com.bottega.documentmanagement.domain.EmployeeId;
import pl.com.bottega.documentmanagement.domain.Role;
import pl.com.bottega.documentmanagement.domain.Role_;
import pl.com.bottega.documentmanagement.domain.repositories.EmployeeRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
        List<Employee> employee =  entityManager.
                createQuery("SELECT e " +
                                "from Employee e " +
                                "where login=:login and hashedPassword=:hashedPassword",
                        Employee.class).
                setParameter("login",login).
                setParameter("hashedPassword",hashedPassword).getResultList();
        if (employee.size() == 0)
            return null;
        else
            return employee.get(0);
    }
    @Override
    public Collection<Role> getRoles(Set<String> roleNames) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> criteriaQuery = criteriaBuilder.createQuery(Role.class);
        Root<Role> root = criteriaQuery.from(Role.class);
        criteriaQuery.select(root);
        criteriaQuery.where(root.get(Role_.name).in(roleNames));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }
    @Override
    public Set<Employee> findByEmployeeIds(Iterable<EmployeeId> ids) {
        Query q = entityManager.createQuery("FROM Employee WHERE employeeId in :ids");
        q.setParameter("ids", ids);
        return Sets.newHashSet(q.getResultList());
    }
}
