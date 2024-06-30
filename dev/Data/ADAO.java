package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public abstract class ADAO implements IDAO{
    protected static String DB_URL = "jdbc:sqlite:ADSS_Group_S/dev/DataBase";
    protected String table_name;

    public void update(Map<String, Object> fieldsAndValuesConditions, Map<String, Object> fieldsAndValuesToUpdates) {
        StringBuilder sql = new StringBuilder("UPDATE " + table_name + " SET ");
        int fieldCount = fieldsAndValuesToUpdates.size();
        int index = 0;

        for (String field : fieldsAndValuesToUpdates.keySet()) {
            sql.append(field).append(" = ?");
            if (index < fieldCount - 1) {
                sql.append(", ");
            }
            index++;
        }

        sql.append(" WHERE ");

        index = 0;

        for (String field : fieldsAndValuesConditions.keySet()) {
            sql.append(field).append(" = ?");
            if (index < fieldCount - 1) {
                sql.append(", ");
            }
            index++;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            index = 1;
            for (Object value : fieldsAndValuesToUpdates.values()) {
                stmt.setObject(index++, value);
            }
            for (Object condition : fieldsAndValuesConditions.values()) {
                stmt.setObject(index++, condition);
            }

            stmt.executeUpdate();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
