package DataAccess;

import Domain.Worker;
import Domain.Role;
import Domain.WorkConditions;

import java.util.HashMap;

public class WorkerDao implements Dao<Worker> {
    private static final HashMap<String, Worker> workers = new HashMap<>();
    private static final WorkerDao instance = new WorkerDao();

    public static WorkerDao getInstance() {
        return instance;
    }

    private WorkerDao() {
        // MANAGER user.
        WorkConditions workConditions = new WorkConditions("", "", "", 120.4);
        workers.put("208395608", new Worker("208395608", "Shai", "111", workConditions, new Role("Manager"), "123", "123"));
        workers.get("208395608").setManager(true);
    }


    @Override
    public HashMap<String, Worker> getAll() {
        return workers;
    }

    // Save new worker
    @Override
    public void save(Worker worker) {
        this.workers.put(String.valueOf(worker.getID()), worker);
    }

    // Update the workers list
    @Override
    public void update(Worker worker) {
        // null check & existing in our hashmap
        if (worker != null & workers.get(String.valueOf(worker.getID())).equals(worker)) {
            this.workers.put(String.valueOf(worker.getID()), worker);
        }
    }

    // Delete Existing Worker
    // No one wants to delete their worker history. We will just mark them as not active.
    @Override
    public void delete(Worker worker) {
        if (worker != null) this.workers.remove(String.valueOf(worker.getID()));
    }

    @Override
    public void addFakeData() {
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
        // shift Manager
        Worker worker9 = new Worker("9", "Hey There", "1234", workConditions1, new Role("ShiftManager"), "9", "123");
        worker9.setManager(true);
        // HR Manager
        Worker worker10 = new Worker("10", "Give me 100", "1234", workConditions1, new Role("HRManager"), "10", "123");
        worker10.setManager(true);

        // add to db
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
