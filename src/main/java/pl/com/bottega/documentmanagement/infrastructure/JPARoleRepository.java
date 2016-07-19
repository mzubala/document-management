package pl.com.bottega.documentmanagement.infrastructure;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.documentmanagement.domain.Role;
import pl.com.bottega.documentmanagement.domain.repositories.RoleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Dell on 2016-07-19.
 */
@Repository
public class JPARoleRepository implements RoleRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void save(Role role) {
        if (!checkIfRoleExists(role.getName()))
        entityManager.merge(role);
    }

    @Override
    @Transactional
    public boolean checkIfRoleExists(String name) {
        return entityManager.createQuery("SELECT count(r) " +
                        "FROM Role r " +
                        "WHERE name =:name", Long.class).
                setParameter("name", name).
                getSingleResult() > 0;
    }
}
