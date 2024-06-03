package DataAccess;

import Domain.Shift;

import java.util.HashMap;
import java.util.Map;

public class ShiftDao implements Dao<Shift> {
    private final HashMap<String, Shift> allShifts = new HashMap<>();
    private static final ShiftDao instance = new ShiftDao();

    public static ShiftDao getInstance() {
        return instance;
    }

    public HashMap<String, Shift> getAllShifts() {
        return allShifts;
    }

    @Override
    public HashMap<String, Shift> getAll() {
        return allShifts;
    }

    @Override
    public void save(Shift shift) {


    }

    @Override
    public void update(Shift shift) {

    }

    @Override
    public void delete(Shift shift) {

    }
}
