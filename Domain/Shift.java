package Domain;

import java.util.ArrayList;

public class Shift {
    // enum of shiftType
    private enum sTypes {Morning, Evening}

    ArrayList<Constraint> constraints;
    Worker shiftManager;
    boolean isActive;
    Date shiftDate;

    // Constructor
    public Shift(Date shiftDate) {
        // init the List
        this.constraints = new ArrayList<>();
        this.shiftDate = shiftDate;
        // shift not
        this.shiftManager = null;
        //  shift not active at first.
        this.isActive = false;
    }

    // Getters & Setters
    public ArrayList<Constraint> getConstraints() {
        return constraints;
    }

    public Worker getShiftManager() {
        return shiftManager;
    }

    public void setShiftManager(Worker shiftManager) {
        this.shiftManager = shiftManager;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Date getShiftDate() {
        return shiftDate;
    }


}
