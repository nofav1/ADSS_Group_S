package Service;

import DataAccess.*;
import Domain.Role;

import java.util.HashMap;
import java.util.List;

public class RoleController {
    Dao roleDao;

    public RoleController() {
        roleDao = RoleDao.getInstance();
    }

    // return all roles
    public HashMap<String, Role> getRoles() {
        return (HashMap<String, Role>) roleDao.getAll();
    }

    public void addRole(String name) {
        Role newRole = new Role(name);
        roleDao.save(newRole);
    }

    public void deleteRole(String name) {
        Role role = new Role(name);
        roleDao.delete(role);
    }

    public void updateRole(String name) {
        Role role = new Role(name);
        roleDao.update(role);

    }
}
