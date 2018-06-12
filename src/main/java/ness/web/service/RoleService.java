package ness.web.service;

import ness.web.model.Role;

import java.util.List;

public interface RoleService {

    void addRole(Role role);
    void updateRole(Role role);
    void removeRole(Role role);
    void removeRole(int id);
    Role getRoleById(int id);
    Role findRoleByName(String name);
    List<Role> getRoles();

}
