package DataAccess;

import Domain.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class RoleDao implements Dao<Role> {
    private final HashMap<String, Role> allRoles = new HashMap<>();
    private static final RoleDao instance = new RoleDao();
    private static int idx = 3;

    public static RoleDao getInstance() {
        instance.allRoles.put("0", new Role("Cashier"));
        instance.allRoles.put("1", new Role("Driver"));
        instance.allRoles.put("2", new Role("Warehouse"));
        return instance;
    }

    @Override
    public HashMap<String, Role> getAll() {
        return allRoles;
    }

    @Override
    public void save(Role role) {
        allRoles.put(String.valueOf(idx), role);
        idx++;

    }

    @Override
    public void update(Role role) {
        for (int i = 0; i < allRoles.size(); i++) {
            Role otherRole = allRoles.get(String.valueOf(i));
            String key = String.valueOf(i);
            if (role.equals(otherRole)) {
                allRoles.put(key, role);
                break; // STOP WHEN FOUND&UPDATED.
            }
        }
    }


    @Override
    public void delete(Role role) {
        for (Role value : allRoles.values()) {
            if (value.getName().equals(role.getName())) {
                allRoles.remove(value.getName());
            }
        }
    }
}
