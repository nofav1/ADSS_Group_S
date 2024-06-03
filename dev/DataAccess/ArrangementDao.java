package DataAccess;

import Domain.Arrangement;

import java.util.HashMap;

public class ArrangementDao implements Dao<Arrangement> {
    private final HashMap<String, Arrangement> arrangements = new HashMap<>();
    private final static ArrangementDao instance = new ArrangementDao();

    public static ArrangementDao getInstance() {
        return instance;
    }

    public HashMap<String, Arrangement> getArrangements() {
        return arrangements;
    }

    @Override
    public HashMap<String, Arrangement> getAll() {
        return null;
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

    @Override
    public void delete(Arrangement arrangement) {
        arrangements.remove(arrangement.getStartDate());
    }
}
