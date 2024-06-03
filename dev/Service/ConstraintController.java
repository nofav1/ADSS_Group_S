package Service;

import DataAccess.ConstraintDao;
import DataAccess.Dao;
import Domain.Constraint;
import Domain.Shift;
import Domain.Worker;

import java.util.HashMap;
import java.util.List;

public class ConstraintController {
    private Dao constraintDao;

    // Constructor
    public ConstraintController() {
        constraintDao = ConstraintDao.getInstance();
    }

    public Constraint createConstraint(Shift shift, Worker worker, Shift.sTypes sType) {
        // Create new constraint
        Constraint currConstraint = new Constraint(worker, shift);
        currConstraint.setShiftType(sType);

        // save it in DB
        constraintDao.save(currConstraint);
        return currConstraint;
    }

    public HashMap<String, Constraint> getAllConstraints() {
        return (HashMap<String, Constraint>) constraintDao.getAll();
    }

    public void deleteConstraint(Constraint constraint) {
        constraintDao.delete(constraint);
    }

    public void updateConstraint(Constraint constraint) {
        constraintDao.update(constraint);
    }


}
