package DataAccess;

import Domain.Worker;
import Domain.Role;
import Domain.WorkConditions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class WorkerDao implements Dao<Worker> {
    private static final WorkerDao instance = new WorkerDao();
    private final Database db;
    HashMap<String, Worker> workers = new HashMap<>();
    private boolean isFirstRun=false;
    public static WorkerDao getInstance() {
        return instance;
    }

    private WorkerDao() {
        // MANAGER user.
        db = Database.getInstance();

        getAll(); // fetch Existing DB into class workers hashmap

        // Create HR manager user
        if (workers.isEmpty()) { // First run
            isFirstRun=true;
            WorkConditions workConditions = new WorkConditions("", "", "", 120.4);
            Role managerRole = new Role("Manager");
            Worker manager = new Worker("208395608", "Shai", "111", workConditions, managerRole, "123", "123");
            manager.setManager(true);
            save(manager);
            getAll();   // update workers map
        }

    }

    @Override
    public HashMap<String, Worker> getAll() {
        HashMap<String, Worker> workers = new HashMap<>();
        String sql = "SELECT * FROM workers";

        try (Connection conn = db.getmConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("ID");
                String name = rs.getString("name");
                String bankAccount = rs.getString("bankAccount");
                String rolesStr = rs.getString("roles");
                String password = rs.getString("password");
                String branch = rs.getString("branch");
                boolean isManager = rs.getInt("isManager") == 1;
                boolean isActive = rs.getInt("isActive") == 1;

                WorkConditions workConditions = new WorkConditions("", "", "", 0); // Convert workConditionsStr to WorkConditions object
                Role role = new Role(rolesStr); // Convert rolesStr to Role object
                Worker worker = new Worker(id, name, bankAccount, workConditions, role, password, branch);
                worker.setManager(isManager);
                worker.setActive(isActive);
                workers.put(id, worker);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        // either way, update the class map
        this.workers = workers;

        return workers;
    }

    @Override
    public void save(Worker worker) {
        if (worker == null) return; // safety check
//        String workerConditionsSql = "INSERT INTO work_conditions(ID,startDate,directManager,workType,salary) VALUES(?,?,?,?,?)";
        String sql = "INSERT INTO workers(ID, name, bankAccount,workConditions, roles, password, branch, isManager, isActive) VALUES(?,?,?,?,?,?,?,?,?)";

        try (Connection conn = db.getmConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, worker.getID());
            pstmt.setString(2, worker.getName());
            pstmt.setString(3, worker.getBankAccount());
            pstmt.setString(4,worker.getWorkConditions().conditionsToJSON());
            pstmt.setString(5, worker.getRole().getName());
            pstmt.setString(6, worker.getPassword());
            pstmt.setString(7, worker.getBranch());
            pstmt.setInt(8, worker.isManager() ? 1 : 0);
            pstmt.setInt(9, worker.isActive() ? 1 : 0);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Failed to save worker. Please try again");
        }

/*        // Save worker Conditions as well
        try (Connection conn = db.getmConnection();
             PreparedStatement pstmt = conn.prepareStatement(workerConditionsSql)) {

            pstmt.setString(1, worker.getID());
            pstmt.setString(2, worker.getWorkConditions().getStartDate());
            pstmt.setString(3, worker.getWorkConditions().getDirectManager());
            pstmt.setString(4, worker.getWorkConditions().getWorkType());
            pstmt.setDouble(5, worker.getWorkConditions().getSalary());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }*/


    }

    @Override
    public void update(Worker worker) {
        if (worker == null) return; // Safety check

        String sqlConditions = "UPDATE work_conditions SET startDate = ?, directManager = ?, workType = ?, salary=? WHERE ID = ?";
        String sql = "UPDATE workers SET name = ?, bankAccount = ?, workConditions = ?, roles = ?, password = ?, branch = ?, isManager = ?, isActive = ? WHERE ID = ?";

        // Workers table
        try (Connection conn = db.getmConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, worker.getName());
            pstmt.setString(2, worker.getBankAccount());
            pstmt.setString(3, worker.getWorkConditions().conditionsToJSON()); // Convert WorkConditions object to string
            pstmt.setString(4, worker.getRole().getName());
            pstmt.setString(5, worker.getPassword());
            pstmt.setString(6, worker.getBranch());
            pstmt.setInt(7, worker.isManager() ? 1 : 0);
            pstmt.setInt(8, worker.isActive() ? 1 : 0);
            pstmt.setString(9, worker.getID());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Failed to update worker. Please try again");
        }
 /*       // Worker conditions table
        try (Connection conn = db.getmConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlConditions)) {

            pstmt.setString(1, worker.getWorkConditions().getStartDate());
            pstmt.setString(2, worker.getWorkConditions().getDirectManager());
            pstmt.setString(3, worker.getWorkConditions().getWorkType());
            pstmt.setDouble(4, worker.getWorkConditions().getSalary());
            pstmt.setString(5, worker.getID());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
*/
        getAll(); // update workers hashmap
    }


    // Deletion of workers - Making it not active
    // Assuming Workers cant be deleted. History must be persistent.
    @Override
    public void delete(Worker worker) {
        if (worker == null) return;
        String sql = "UPDATE workers SET  isActive = ? WHERE ID = ?";


        try (Connection conn = db.getmConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, 0); // NOT ACTIVE ANYMORE.

            pstmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void addFakeData() {
        if (!isFirstRun && workers.size()>1) return ; // Run this code only once. ( WHEN DB WITHOUT DATA )
        WorkConditions workConditions = new WorkConditions("01/06/2024", "Arnon", "Full", 1204);
        WorkConditions workConditions1 = new WorkConditions("01/06/2024", "Maxim", "Full", 2000);
        Worker worker1 = new Worker("1", "John Doe", "111", workConditions, new Role("Manager"), "1", "123");
        Worker worker2 = new Worker("2", "Lance Rough", "1234", workConditions1, new Role("Co-Founder"), "2", "123");
        Worker worker3 = new Worker("3", "Hey Nice", "1234", workConditions, new Role("Role11"), "3", "123");
        Worker worker4 = new Worker("4", "J Smith", "1234", workConditions, new Role("Role12"), "4", "123");
        Worker worker5 = new Worker("5", "Role Bauer", "1234", workConditions1, new Role("Role13"), "5", "123");
        Worker worker6 = new Worker("6", "Joe Coe", "1234", workConditions, new Role("Role14"), "6", "123");
        Worker worker7 = new Worker("7", "Moe Low", "1234", workConditions1, new Role("Coder"), "7", "123");
        Worker worker8 = new Worker("8", "Hoe Doe", "1234", workConditions, new Role("Advertisor"), "8", "123");
        Worker worker9 = new Worker("9", "Hey There", "1234", workConditions1, new Role("ShiftManager"), "9", "123");
        worker9.setManager(true);
        Worker worker10 = new Worker("10", "Give me 100", "1234", workConditions1, new Role("HRManager"), "10", "123");
        worker10.setManager(true);

        save(worker1);
        save(worker2);
        save(worker3);
        save(worker4);
        save(worker5);
        save(worker6);
        save(worker7);
        save(worker8);
        save(worker9);
        save(worker10);
    }
}
