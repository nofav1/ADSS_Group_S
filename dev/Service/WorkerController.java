package Service;

import DataAccess.Dao;
import DataAccess.WorkerDao;
import Domain.Worker;

import java.util.ArrayList;
import java.util.List;

public class WorkerController {
    Dao workersDao;
    Worker currWorker;

    public WorkerController() {
        workersDao = WorkerDao.getInstance();
    }

    // get specific worker by id
    public Worker getWorker(int id) {
        // no problem with casting in runtime.
        Worker resultWorker = null;
        for (Worker worker : new ArrayList<Worker>(workersDao.getAll().values())) {
            // Same ID? return the worker.
            if (worker.getID() == id) resultWorker = worker;
        }
        // Can be null
        return resultWorker;
    }


    // get all workers list.
    public List<Worker> getAllWorkers() {
        return (List<Worker>) workersDao.getAll().values().stream().toList();
    }    // get all workers list.


    // get Active Workers ONLY.
    public List<Worker> getAllActiveWorkers() {
        List<Worker> activeWorkers = new ArrayList<Worker>();
        for (Worker worker : getAllWorkers()) {
            if (worker.isActive()) activeWorkers.add(worker);
        }
        return activeWorkers;
    }

}
