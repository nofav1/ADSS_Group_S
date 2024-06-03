package DataAccess;

import Domain.Constraint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstraintDao implements Dao<Constraint> {
    private final HashMap<String, List<Constraint>> constraints = new HashMap<>();
    private final static ConstraintDao instance = new ConstraintDao();

    public static ConstraintDao getInstance() {
        return instance;

    }

    public HashMap<String, List<Constraint>> getConstraints() {
        return constraints;
    }


    @Override
    public HashMap<String, Constraint> getAll() {
        return null;
    }

    @Override
    public void save(Constraint constraint) {
//        if (constraint != null)
            // Date:Constraint
//            constraints.put(constraint.getCurrShift().getShiftDate(), );
    }

    @Override
    public void update(Constraint constraint) {
//        if (constraint != null && constraints.contains(constraint)) {
//            constraints.set(constraints.indexOf(constraint), constraint);
        }

    @Override
    public void delete(Constraint constraint) {
        //        if (constraint != null && constraints.contains(constraint)) {
        // delete constraint by date and ID.
//            constraints.remove(constraint.getCurrShift().getShiftDate());
    }
}

