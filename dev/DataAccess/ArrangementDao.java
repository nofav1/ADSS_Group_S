package DataAccess;

import Domain.Arrangement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ArrangementDao implements Dao<Arrangement> {
    private HashMap<String, Arrangement> arrangements = new HashMap<>();
    private final static ArrangementDao instance = new ArrangementDao();

    Database db;

    public static ArrangementDao getInstance() {
        return instance;
    }

    public HashMap<String, Arrangement> getArrangements() {
        return arrangements;
    }

    private ArrangementDao() {
        db = Database.getInstance();
        getAll();// fetch Existing db
        if (arrangements.isEmpty()) { // DB first run
            // initialize first week to prevent bugs.
            List<String> currentWeekDates = Arrangement.createFirstArrangement();
            Arrangement arrangement = new Arrangement(currentWeekDates);

            save(arrangement); // Save in DB
        }

    }

    @Override
    public HashMap<String, Arrangement> getAll() {
        HashMap<String, Arrangement> arrangements = new HashMap<>();
        String sql = "SELECT * FROM arrangements";

        try (Connection conn = db.getmConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String startDate = rs.getString("startDate");
                String endDate = rs.getString("endDate");
                String managerID = rs.getString("managerID");
                String weeklyShifts = rs.getString("weeklyShifts");
                Arrangement arrangement = new Arrangement(startDate, endDate, managerID, weeklyShifts);
                arrangements.put(startDate, arrangement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.arrangements = arrangements;
        return arrangements;
    }

    @Override
    public void save(Arrangement arrangement) {
        String sql = "INSERT INTO arrangements(startDate, endDate, managerID, weeklyShifts) VALUES(?,?,?,?)";
        if (arrangement != null && !arrangements.containsValue(arrangement))
            try (Connection conn = db.getmConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, arrangement.getStartDate());
                pstmt.setString(2, arrangement.getEndDate());
                pstmt.setString(3, arrangement.getManager());
                pstmt.setString(4, arrangement.shiftsToJson()); // Json'ize the shifts list

                pstmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        arrangements.put(arrangement.getStartDate(), arrangement);
    }

    @Override
    public void update(Arrangement arrangement) {
        String sql = "UPDATE arrangements SET weeklyShifts=? WHERE startDate = ?";
        if (arrangement != null && arrangements.containsValue(arrangement))
            try (Connection conn = db.getmConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, arrangement.shiftsToJson()); // Json'ize the shifts list
                pstmt.setString(2, arrangement.getStartDate());

                pstmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        arrangements.put(arrangement.getStartDate(), arrangement);
    }

//Assuming - No such thing arrangement deletion. EVERYTHING IS HISTORY!
@Override
public void delete(Arrangement arrangement) {
}

// No need to create arrangement as fake data, we LET the client create it with Workers&Roles - To learn to how to use the system.
@Override
public void addFakeData() {
}
}
