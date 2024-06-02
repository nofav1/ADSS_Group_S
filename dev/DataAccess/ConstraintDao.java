package DataAccess;

import Domain.Constraint;

import java.util.List;

public class ConstraintDao implements Dao<Constraint> {
    List<Constraint> constraints;

    @Override
    public List<Constraint> getAll() {
        return constraints;
    }

    @Override
    public void save(Constraint constraint) {
        constraints.add(constraint);
    }

    @Override
    public void update(Constraint constraint) {
        if (constraint != null && constraints.contains(constraint)) {
            constraints.set(constraints.indexOf(constraint), constraint);
        }
    }

    @Override
    public void delete(Constraint constraint) {
        if (constraint != null && constraints.contains(constraint)) {
            constraints.remove(constraint);
        }
    }
}
