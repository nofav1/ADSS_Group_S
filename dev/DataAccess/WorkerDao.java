package DataAccess;

import Domain.Worker;

import java.util.ArrayList;
import java.util.List;

public class WorkerDao implements Dao<Worker> {
    private final List<Worker> workers = new ArrayList<>();
    private static final WorkerDao instance = new WorkerDao();

    public static WorkerDao getInstance() {
        return instance;
    }

    private WorkerDao() {
        // Add some fake workers ...

    }

    // get all workers
    @Override
    public List<Worker> getAll() {
        return this.workers;
    }



    // Save new worker
    @Override
    public void save(Worker worker) {
        this.workers.add(worker);
    }

    // Update the workers list
    @Override
    public void update(Worker worker) {
        if (worker != null & workers.contains(worker)) {
            this.workers.set(workers.indexOf(worker), worker);
        }
    }

    // Delete Existing Worker
    @Override
    public void delete(Worker worker) {
        if (worker != null) this.workers.remove(worker);
    }
}
