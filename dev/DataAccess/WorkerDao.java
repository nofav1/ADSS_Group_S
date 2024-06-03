package DataAccess;

import Domain.Role;
import Domain.SystemDate;
import Domain.WorkConditions;
import Domain.Worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorkerDao implements Dao<Worker> {
    private static final HashMap<String, Worker> workers = new HashMap<>();
    private static final WorkerDao instance = new WorkerDao();

    public static WorkerDao getInstance() {
        return instance;
    }

    private WorkerDao() {
        // Add some fake workers ...
        WorkConditions workConditions = new WorkConditions();
        workers.put("208395608",new Worker(208395608,"Shai",111,workConditions,new ArrayList<>(),"123","123"));
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
    @Override
    public void delete(Worker worker) {
        if (worker != null) this.workers.remove(String.valueOf(worker.getID()));
    }
}
