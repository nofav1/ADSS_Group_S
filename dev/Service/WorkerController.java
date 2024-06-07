package Service;

import DataAccess.WorkerDao;
import Domain.Worker;
import DataAccess.Dao;
import Domain.Role;
import Domain.WorkConditions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorkerController implements IController {
    Dao workersDao;
    Worker currWorker, selectedWorker;

    public WorkerController() {
        workersDao = WorkerDao.getInstance();
    }

    // Create NEW worker
    public void createWorker(String ID, String name, String bankAccount, String startDate, String director, String workType, double salary, Role role, String password, String branch) {
        // create new Worker WorkConditions
        WorkConditions workConditions = new WorkConditions(startDate, director, workType, salary);
        // create the new worker
        Worker newWorker = new Worker(ID, name, bankAccount, workConditions, role, password, branch);
        // Save it in DB
        workersDao.save(newWorker);
    }

    // get specific worker by id
    public Worker getWorker(String id) {
        // no problem with casting in runtime.
        Worker resultWorker = null;
        for (Worker worker : new ArrayList<Worker>(workersDao.getAll().values())) {
            // Same ID? return the worker.
            if (worker.getID().equals(id)) resultWorker = worker;
        }
        // Can be null
        return resultWorker;
    }

    public void updateWorker(Worker worker) {
        workersDao.update(worker);
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

    // Getter & Setter
    public Worker getCurrWorker() {
        return currWorker;
    }

    public void setCurrWorker(Worker currWorker) {
        this.currWorker = currWorker;
    }

    public void selectWorker(Worker worker) {
        this.selectedWorker = worker;
    }

    public Worker getSelectedWorker() {
        return selectedWorker;
    }

    @Override
    public void loadFakeData() {
        workersDao.addFakeData();
    }
}
