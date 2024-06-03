package Domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.String;

public class Arrangement {
    private Worker manager;

    // TODO : Dates in shifts already.  Data duplication .
    private String startDate;
    private String endDate;
    private List<Shift> weeklyShifts;

    // Constructor
    public Arrangement(Worker manager, String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.manager = manager;
        // create new week shifts ( 6 days a week)
        this.weeklyShifts = new ArrayList<>(6);
        for (Shift shift : weeklyShifts) {
//            shift.shiftDate
        }
    }

    // Getters //
    public Worker getManager() {
        return manager;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public List<Shift> getWeeklyShifts() {
        return weeklyShifts;
    }

    public Shift select(int id) {
        return weeklyShifts.get(id);
    }

    // equals by same type and dates range.(Only 1 arrangement for each week.)
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Arrangement otherArrangement) {
            return otherArrangement.startDate.equals(this.startDate) && endDate.equals(otherArrangement.endDate);
        }
        return false;
    }
}
