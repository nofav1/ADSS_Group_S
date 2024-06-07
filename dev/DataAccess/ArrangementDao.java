package DataAccess;

import Domain.Arrangement;

import java.util.HashMap;
import java.util.List;

public class ArrangementDao implements Dao<Arrangement> {
    private final HashMap<String, Arrangement> arrangements = new HashMap<>();
    private final static ArrangementDao instance = new ArrangementDao();

    public static ArrangementDao getInstance() {
        return instance;
    }

    public HashMap<String, Arrangement> getArrangements() {
        return arrangements;
    }

    private ArrangementDao() {
        // initialize first week to prevent bugs.
        List<String> currentWeekDates = Arrangement.createFirstArrangement();
        Arrangement arrangement = new Arrangement(currentWeekDates);
        arrangements.put(currentWeekDates.getFirst(), arrangement);
    }

    @Override
    public HashMap<String, Arrangement> getAll() {
        return arrangements;
    }

    @Override
    public void save(Arrangement arrangement) {
        if (arrangement != null && !arrangements.containsValue(arrangement))
            arrangements.put(arrangement.getStartDate(), arrangement);
    }

    @Override
    public void update(Arrangement arrangement) {
        if (arrangement != null & arrangements.containsValue(arrangement)) {
            this.arrangements.put(arrangement.getStartDate(), arrangement);
        }
    }

    // No such thing arrangement deletion. EVERYTHING IS HISTORY!
    @Override
    public void delete(Arrangement arrangement) {}

    @Override
    public void addFakeData() {

    }
}
