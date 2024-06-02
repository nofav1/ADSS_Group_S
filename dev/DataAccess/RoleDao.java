package DataAccess;

import Domain.Role;
import java.util.ArrayList;
import java.util.List;

public class RoleDao implements Dao<Role> {
    List<Role> allRoles = new ArrayList<Role>();
    private static final RoleDao instance = new RoleDao();

    public static RoleDao getInstance() {
        return instance;
    }

    @Override
    public List<Role> getAll() {
        return allRoles;
    }

    @Override
    public void save(Role role) {
        allRoles.add(role);
    }

    @Override
    public void update(Role role) {}

    @Override
    public void delete(Role role) {
        allRoles.remove(role);
    }
}
