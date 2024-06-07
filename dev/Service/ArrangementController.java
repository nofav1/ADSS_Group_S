package Service;


import DataAccess.ArrangementDao;
import DataAccess.Dao;
import Domain.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class ArrangementController implements IController {
    Dao arrangementDao;
    Shift currShift;
    Arrangement currArrangement;

    public ArrangementController() {
        arrangementDao = ArrangementDao.getInstance();
    }

    public Arrangement getCurrentArrangement() {
        int i = arrangementDao.getAll().size() - 1;
        return (Arrangement) arrangementDao.getAll().get(i); // get last arrangement
    }

    public HashMap<String, Arrangement> getArrangements() {
        return (HashMap<String, Arrangement>) arrangementDao.getAll();
    }

    public void updateArrangement(Arrangement arrangement) {
        if (arrangementDao.getAll().containsValue(arrangement))
            arrangementDao.update(arrangement);
    }

    public Arrangement createGetArrangement() {
        Arrangement result;
        if (arrangementDao.getAll().isEmpty()) {
            // First run.
            Arrangement arrangement = new Arrangement();
            // {Date:Arrangement}
            arrangementDao.getAll().put(arrangement.getStartDate(), arrangement);
            // success
            result = arrangement;
        } else {
            Calendar calendar = Calendar.getInstance();
            // add 1 week
            calendar.add(Calendar.WEEK_OF_YEAR, 1);
            // move to sundays at next week
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            // There are arrangements already in DB
            String formattedToday = new SystemDate(calendar.getTime()).getDateString();
            // Find the arrangement that have the shift with the date.
            // No need to create New Arrangement.
            result = getShift(formattedToday);
            if (result == null) {
                // Traversed it all, and no arrangement found? Create new one.
                result = new Arrangement();
                // Save it in DB
                arrangementDao.getAll().put(result.getStartDate(), result);
            }
        }
        // set current arrangement as result
        setCurrArrangement(result);
        return result;
    }


    public Arrangement getShift(String date) {
        Arrangement result = null;
        for (int i = 0; i < arrangementDao.getAll().size(); i++) {
            Arrangement arrangement = (Arrangement) arrangementDao.getAll().values().stream().toList().get(i);
            // Check if input date is in Shifts in current arrangement.
            for (Shift shift : arrangement.getWeeklyShifts()) {
                if (shift.getShiftDate().equals(date)) {
                    result = arrangement;
                    break;
                }
            }
            if (result != null) break;
        }
        return result;
    }

    // Getter & Setter for Screen communication. //
    public void selectShift(Shift shift) {
        this.currShift = shift;
    }

    public Shift getCurrentShift() {
        return currShift;
    }

    public String getWeekDatesString() {
        StringBuilder res = new StringBuilder();
        // NoNull - no need to check.
        Arrangement arrangement = createGetArrangement();
        int i = 0;
        for (Shift shift : arrangement.getWeeklyShifts()) {
            res.append(i + 1).append(") ").append(shift.getShiftDate() + "\n");
            i++;
        }
        return res.toString();
    }

    // Getter & Setter //
    public Arrangement getCurrArrangement() {
        return currArrangement;
    }

    public void setCurrArrangement(Arrangement currArrangement) {
        this.currArrangement = currArrangement;
    }


    public List<String> getWorkersByConstraints(boolean isManager) {
        List<String> workersID = new ArrayList<>();
        for (int i = 0; i < getCurrentShift().getConstraints().size(); i++) {
            Worker currWorker = getCurrentShift().getConstraints().get(i).getCurrWorker();
            // Add a worker - if only manager We want&&current worker is actually a manager
            // Or Add a worker if we asked regular workers.
            if ((isManager && currWorker.isManager()) || !isManager)
                workersID.add(currWorker.getID());
        }
        return workersID;
    }

    public String getConstraintType(String id) {
        for (Constraint constraint : getCurrentShift().getConstraints()) {
            if (constraint.getCurrWorker().getID().equals(id))
                return constraint.getShiftType();
        }

        return null;
    }


    public void getRolesString(List<Role> roles, StringBuilder stringBuilder) {
        for (Role role : roles) {
            stringBuilder.append(role.getName()).append(",");
        }
    }

    public String allShiftsHistory() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < arrangementDao.getAll().size(); i++) {
            Arrangement arrangement = (Arrangement) arrangementDao.getAll().get(i);
            for (Shift shift : arrangement.getWeeklyShifts()) {
                res.append(shift).append("\n");
            }
        }
        return res.toString();
    }

    public String shiftString(Shift shift) {
        StringBuilder res = new StringBuilder();
        res.append("Day: ").append(shift.getShiftDate()).append("\n");
        res.append("ShiftManager: ").append(shift.getShiftManager())
                .append("Shift Manager Evening: ").append(shift.getShiftManagerEvening()).append("\nWorkers: \n");
        res.append(shift.workersByShiftString());
        return res.toString();
    }

    @Override
    public void loadFakeData() {
    }
}
