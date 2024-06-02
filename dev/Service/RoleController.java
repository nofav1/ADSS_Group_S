package Service;

import DataAccess.Dao;
import DataAccess.RoleDao;
import Domain.Role;

import java.util.List;

public class RoleController {
    Dao roleDao;

    public RoleController() {
        new DataAccess.RoleDao();
        roleDao = RoleDao.getInstance();

    }

    // return all roles
    public List<Role> getRoles() {
        return (List<Role>) roleDao.getAll();
    }

    public void addRole(String name) {
        Role newRole = new Role(name);
        roleDao.save(name);
    }

    public void deleteRole(Role role) {
        roleDao.delete(role);
    }

    public void updateRole(Role role) {
            roleDao.update(role);

    }
}
