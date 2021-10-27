package ru.dmitry.seleznev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dmitry.seleznev.dao.RoleDAO;
import ru.dmitry.seleznev.model.Role;

import java.util.HashSet;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public void saveRole(Role role) {
        roleDAO.saveRole(role);
    }

    @Override
    public Role getRole(String role) {
        return roleDAO.getRole(role);
    }

    @Override
    @Transactional
    public Set<Role> getRoleSet(Set<Role> roleSet) {
        Set<Role> resultSet = new HashSet<>();
        for (Role r:
             roleSet) {
            try {
                resultSet.add(getRole(r.getRole().substring(5)));
            } catch (EmptyResultDataAccessException e) {
                saveRole(new Role(r.getRole()));
                resultSet.add(getRole(r.getRole().substring(5)));
            }
        }
        return resultSet;
    }
}
