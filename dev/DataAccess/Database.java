package DataAccess;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * Singleton DB class.
 */
public class Database {

    // Singleton class
    private static Database INSTANCE;
    private Connection mConnection;
    // URL for the database
    private String URL = "jdbc:sqlite:DataAccess:mDB.db";

    // empty constructor
    private Database() {
        // Creating the tables in SQLite DB
        createGetDB();
//        if (createGetDB())
//            Logger.getAnonymousLogger().info("Database ta/bles creation success");
//        else Logger.getAnonymousLogger().info("Database tables creation failed");
    }

    public static Database getInstance() {
        if (INSTANCE == null) INSTANCE = new Database();
        return INSTANCE;
    }

    public Connection getmConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        } catch (SQLException | ClassNotFoundException e) {
            return null;
        }
    }

    // Create tables when DB first initializes
    public boolean createGetDB() {
        boolean isCreated = true;
        try (Connection conn = createFile()) {
            if (conn != null) { // Database FOUND
                /* Create TABLES : */
                this.mConnection = conn;
                createAllTables(conn);
            }
        } catch (SQLException | IOException e) {
            isCreated = false;
        }
        return isCreated;
    }

    // Create workers table
    private boolean createWorkersTable(Statement statement) throws SQLException {
        String sqlWorkers = "CREATE TABLE IF NOT EXISTS workers (\n"
                + "    ID text PRIMARY KEY,\n"
                + "    name text NOT NULL,\n"
                + "    bankAccount text,\n"
                + "    workConditions text,\n"
                + "    roles text,\n"
                + "    password text,\n"
                + "    branch text,\n"
                + "    isManager integer,\n"
                + "    isActive integer\n"
                + ");";
        return statement.execute(sqlWorkers);
    }

    // Create Arrangements tables
    private boolean createArrangementsTable(Statement statement) throws SQLException {
        String sqlArrangements = "CREATE TABLE IF NOT EXISTS arrangements (\n"
                + "    startDate TEXT NOT NULL,\n"
                + "    endDate TEXT NOT NULL,\n"
                + "    managerID ,\n"
                + "    weeklyShifts TEXT NOT NULL,\n"
                + "    PRIMARY KEY (startDate, endDate)\n"
                + ");";
        return statement.execute(sqlArrangements);
    }

    // Create Roles tables
    private boolean createRolesTable(Statement statement) throws SQLException {
        String sqlRoles = "CREATE TABLE IF NOT EXISTS roles (\n"
                + "    id INTEGER PRIMARY KEY AUTOINCREMENT ,\n"
                + "    name text NOT NULL UNIQUE\n"
                + ");";
        return statement.execute(sqlRoles);
    }


    // Create Constraints table
    private boolean createConstraintsTable(Statement statement) throws SQLException {
        String sqlConstraints = "CREATE TABLE IF NOT EXISTS constraints (\n"
                + "    date TEXT PRIMARY KEY NOT NULL,\n"
                + "    workerID TEXT NOT NULL,\n"
                + "    shiftType TEXT NOT NULL,\n"
                + "    FOREIGN KEY (workerID) REFERENCES workers(ID)\n"
                + ");";
        return statement.execute(sqlConstraints);
    }

    // Create Shifts Table
    private boolean createShiftsTable(Statement statement) throws SQLException {
        String sqlShifts = "CREATE TABLE IF NOT EXISTS shifts (\n"
                + "    date TEXT PRIMARY KEY NOT NULL,\n"
                + "    managerID TEXT NOT NULL,\n"
                + "    eveningManagerID TEXT NOT NULL,\n"
                + "    isActive INTEGER NOT NULL,\n"
                + "    workers TEXT NOT NULL,\n"
                + "    constraints TEXT NOT NULL,\n"
                + "    FOREIGN KEY (managerID) REFERENCES workers(ID),\n"
                + "    FOREIGN KEY (eveningManagerID) REFERENCES workers(ID)\n"
                + ");";
        return statement.execute(sqlShifts);
    }

    // Create and connect to the SQLite database file
    private Connection createFile() throws IOException {
        Connection conn = null;
        try {
            File dbFile = new File("dev/DataAccess/mDB.db");
            if (!dbFile.exists()) {
                dbFile.getParentFile().mkdirs();
                dbFile.createNewFile();
            }
            URL = "jdbc:sqlite:" + dbFile.getAbsolutePath();
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(URL);
//            System.out.println("DB: Connection to SQLite has been established.");
            // Enable foreign key support
            try (Statement stmt = conn.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
            }
        } catch (Exception e) {
            System.out.println("Error connecting to SQLite: " + e.getMessage());
        }
        return conn;
    }

    private boolean createAllTables(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        createWorkersTable(statement);
        createRolesTable(statement);
        createArrangementsTable(statement);
        createConstraintsTable(statement);
        createShiftsTable(statement);
        return true;
    }
}
