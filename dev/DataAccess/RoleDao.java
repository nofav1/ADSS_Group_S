package DataAccess;

import Domain.Role;

import java.sql.*;
import java.util.HashMap;

public class RoleDao implements Dao<Role> {
    private final HashMap<String, Role> allRoles = new HashMap<>();
    private static final RoleDao instance = new RoleDao();
    private final Database db;
    private boolean isFirstRun = false;


    public static RoleDao getInstance() {
        return instance;
    }

    // Constructor
    private RoleDao() {
        db = Database.getInstance();
        getAll(); // fetch existing db into hashmap

        if (allRoles.isEmpty()) {
            isFirstRun = true; // First db init
            // Assuming there are ALWAYS 3 Roles from beginning.
            Role cashier = new Role("Cashier");
            Role driver = new Role("Driver");
            Role warehouse = new Role("Warehouse");

            // Add them into the DB.
            save(cashier);
            save(driver);
            save(warehouse);

            // update map of roles
            getAll();
        }
    }

    // load last state of roles table in DB into the roles HashMap
    @Override
    public HashMap<String, Role> getAll() {
        String sql = "SELECT * FROM roles";
        try (Connection conn = db.getmConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                allRoles.put(rs.getString("name"), new Role(rs.getString("name")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allRoles;
    }

    @Override
    public void save(Role role) {
        String sql = "INSERT INTO roles(name) VALUES(?)";
        try (Connection conn = db.getmConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, role.getName());
            pstmt.executeUpdate();
            getAll(); // update the map

        } catch (SQLException e) {
            System.out.println("Role already exists. try different name");
        }
    }

    @Override
    public void update(Role role) {
        String sql = "UPDATE roles SET name = ?";

        try (Connection conn = db.getmConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, role.getName());
        } catch (SQLException e) {
            System.out.println("Role not found. try different name");
        }

        // update the map
        getAll();
    }


    @Override
    public void delete(Role role) {
        String sql = "DELETE FROM roles WHERE name = ?";

        try (Connection conn = db.getmConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, role.getName());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Role not found. Try a different name.");
            } else {
                System.out.println("Role deleted successfully.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        getAll(); // update the map of roles
    }


    @Override
    public void addFakeData() {
        if (!isFirstRun) return; // Build it only once.
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
        isFirstRun = false; // turning off switch.
    }


}
