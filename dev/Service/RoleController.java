package Service;

import DataAccess.Dao;
import DataAccess.RoleDao;
import Domain.Role;

import java.util.HashMap;

public class RoleController implements IController {
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

    public Role getRole(String name) {
        // Safety check
        if (name == null) return null;
        Role result = null;
        for (int i = 0; i < roleDao.getAll().size(); i++) {
            Role role = (Role) roleDao.getAll().get(String.valueOf(i));
            if (role.getName().equals(name)) {
                result = role;
            }
        }
        return result;
    }

    @Override
    public void loadFakeData() {
        roleDao.addFakeData();
    }
}
