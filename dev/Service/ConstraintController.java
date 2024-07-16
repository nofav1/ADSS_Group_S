package Service;

import DataAccess.ConstraintDao;
import DataAccess.Dao;
import Domain.Constraint;
import Domain.Worker;

import java.util.HashMap;

public class ConstraintController implements IController {
    private Dao constraintDao;
    public static int LAST_DAY=-1; // INITIALIZE WHEN Reading Configuration file.


    // Constructor
    public ConstraintController() {
        constraintDao = ConstraintDao.getInstance();
    }

    public Constraint createConstraint(Worker worker, String cType, String date) {
        // Create new constraint
        Constraint currConstraint = new Constraint(worker, cType, date);
        // save it in DB
        constraintDao.save(currConstraint);
        return currConstraint;
    }

    public HashMap<String, Constraint> getAllConstraints() {
        return (HashMap<String, Constraint>) constraintDao.getAll();
    }

    public void deleteConstraint(Worker worker,String cType,String date) {
        constraintDao.delete(new Constraint(worker,cType,date));
    }

    public void updateConstraint(Constraint constraint) {
        constraintDao.update(constraint);
    }

    public static void setLastDay(int lastDay) {
        LAST_DAY = lastDay;
    }

    @Override
    public void loadFakeData() {

    }

}
