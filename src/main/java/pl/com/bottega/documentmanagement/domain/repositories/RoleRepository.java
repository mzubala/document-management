package pl.com.bottega.documentmanagement.domain.repositories;

import pl.com.bottega.documentmanagement.domain.Role;

/**
 * Created by Dell on 2016-07-19.
 */
public interface RoleRepository {

    void save(Role role);

    boolean checkIfRoleExists(String name);
}
