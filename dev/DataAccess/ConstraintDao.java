package DataAccess;

import Domain.Constraint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        // result looks like this:
        // { Date:Constraint }
        HashMap<String, Constraint> res = new HashMap<>();
        for (List<Constraint> value : constraints.values()) {
            for (Constraint constraint : value) {
                res.put(constraint.getDate(), constraint);
            }
        }
        return res;
    }

    @Override
    public void save(Constraint constraint) {
        if (constraint != null)
            // check if the date exists already in constraints map
            if (constraints.get(constraint.getDate()) == null) {
                constraints.put(constraint.getDate(), new ArrayList<>());
            }
        addDeleteConstraints(constraint, true);

    }

    @Override
    public void update(Constraint constraint) {
        if (constraint != null && constraints.get(constraint.getDate()) != null) {
            addDeleteConstraints(constraint, true);
        }
    }

    @Override
    public void delete(Constraint constraint) {
        if (constraint != null)
            //delete constraint by date and ID.
            addDeleteConstraints(constraint, false);
    }

    // Implemented in controller
    @Override
    public void addFakeData() {}


    private  void addDeleteConstraints(Constraint constraint, boolean addOrDelete) {
        for (List<Constraint> value : constraints.values()) {
            for (Constraint otherConstraint : value) {
                // If Dates are same, we found the correspondence list. Add it to this list
                if (constraint.getDate().equals(otherConstraint.getDate())) {
                    // add = true
                    if (addOrDelete) value.add(constraint);
                        // delete = false
                    else value.remove(constraint);
                    return;
                }
            }
        }
    }
}

