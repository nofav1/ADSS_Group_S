package Domain;

import java.util.ArrayList;
import java.util.List;

public class Shift {
    // enum of shiftType
    public String[] shiftTypes = {"Morning", "Evening"};

    private List<Constraint> constraints;
    private List<Worker> workers;
    private Worker shiftManager, shiftManagerEvening;
    private boolean isActive;
    private String shiftDate;

    // Constructor
    public Shift(String shiftDate) {
        // init the Lists
        this.constraints = new ArrayList<>();
        this.workers = new ArrayList<>();

        this.shiftDate = shiftDate;
        // shift not
        this.shiftManager = null;
        this.shiftManagerEvening = null;
        //  shift not active at first.
        this.isActive = false;
    }

    // Getters & Setters


    public void setShiftDate(String shiftDate) {
        this.shiftDate = shiftDate;
    }

    public List<Constraint> getConstraints() {
        return constraints;
    }

    public Worker getShiftManager() {
        return shiftManager;
    }

    public void setShiftManager(Worker shiftManager) {
        for (Constraint constraint : constraints) {
            Worker currWorker = constraint.getCurrWorker();
            if (shiftManager.equals(currWorker))
                if (constraint.getShiftType().equals("Evening")) this.shiftManagerEvening = currWorker;
                else this.shiftManager = currWorker;
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public Worker getShiftManagerEvening() {
        return shiftManagerEvening;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getShiftDate() {
        return shiftDate;
    }


    // If a constraint already working, change its constraint to input.
    // If a constraint not working yet in this shift, add it to constraint list.
    public void addConstraint(Constraint constraint) {
        if (!constraints.contains(constraint))
            constraints.add(constraint);
        else constraints.set(constraints.indexOf(constraint), constraint);
    }

    // If a worker already working, change its shift type.
    // If a worker not working yet in this shift, add him to workers list.
    public void addWorker(Worker worker) {
        if (!workers.contains(worker))
            workers.add(worker);
        else workers.set(workers.indexOf(worker), worker);
    }

    public List<Worker> getWorkers() {
        return workers;
    }


    public String workersByShiftString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            // Shift type
            res.append(shiftTypes[i]).append(":\n");
            // Get all workers in this shiftType.
            for (Constraint constraint : constraints) {
                Worker currWorker = constraint.getCurrWorker();
                // Add worker to result only if same shiftType.
                if (constraint.getShiftType().equals(shiftTypes[i]) && workers.contains(currWorker))
                    res.append(currWorker);
                else if (shiftTypes[i].equals("Double")) res.append(currWorker);
            }
        }
        return res.toString();
    }

    // toString of Shift
    @Override
    public String toString() {
        return "Shift at :" + shiftDate + "\n" +
                workersByShiftString();

    }
}
