package DataAccess;

import Domain.Role;

import java.util.HashMap;

public class RoleDao implements Dao<Role> {
    private final HashMap<String, Role> allRoles = new HashMap<>();
    private static final RoleDao instance = new RoleDao();
    private static int idx = 3;

    public static RoleDao getInstance() {
        return instance;
    }

    private RoleDao() {
        // Assuming there are ALWAYS 3 Roles from beginning.
        Role cashier = new Role("Cashier");
        Role driver = new Role("Driver");
        Role warehouse = new Role("Warehouse");
        cashier.addCantDo(driver);
        cashier.addCantDo(warehouse);
        driver.addCantDo(cashier);
        driver.addCantDo(warehouse);
        warehouse.addCantDo(cashier);
        warehouse.addCantDo(driver);

        // Add them into the DB.
        allRoles.put("0", cashier);
        allRoles.put("1", driver);
        allRoles.put("2", warehouse);
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
        int i = 0;
        for (Role value : allRoles.values()) {
            if (value.equals(role)) {
                allRoles.remove(String.valueOf(i));
            }
            i++;
        }
        idx--;
    }

    @Override
    public void addFakeData() {
        Role role1 = new Role("Role1");
        Role role2 = new Role("Role2");
        Role role3 = new Role("Role3");
        Role role4 = new Role("Role4");
        Role role5 = new Role("Role5");
        Role role6 = new Role("Role6");
        Role role7 = new Role("Role7");
        Role role8 = new Role("Role8");
        Role role9 = new Role("Role9");
        save(role1);
        save(role2);
        save(role3);
        save(role4);
        save(role5);
        save(role6);
        save(role7);
        save(role8);
        save(role9);
    }


}
