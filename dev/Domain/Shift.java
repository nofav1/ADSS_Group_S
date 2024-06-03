package Domain;

import java.util.ArrayList;
import java.util.List;
import java.lang.String;

public class Shift {
    // enum of shiftType
    public enum sTypes {Morning, Evening}

    List<Constraint> constraints;
    Worker shiftManager;
    boolean isActive;
    String shiftDate;

    // Constructor
    public Shift(String shiftDate) {
        // init the List
        this.constraints = new ArrayList<>();
        this.shiftDate = shiftDate;
        // shift not
        this.shiftManager = null;
        //  shift not active at first.
        this.isActive = false;
    }

    // Getters & Setters


    public List<Constraint> getConstraints() {
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

    public String getShiftDate() {
        return shiftDate;
    }


}
