package Data;

import Presentation.menu;
import com.google.gson.JsonObject;
import org.sqlite.core.DB;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ADAO implements IDAO{
    protected static String DB_URL;
    protected String table_name;

    public ADAO() {
        DB_URL = getDataBasePathFromConfig();
    }

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

        int conditionCount = fieldsAndValuesConditions.size();
        index = 0;

        for (String field : fieldsAndValuesConditions.keySet()) {
            sql.append(field).append(" = ?");
            if (index < conditionCount - 1) {
                sql.append(" AND ");
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

    public List<JsonObject> genericSearch(Map<String, Object> fieldsAndValuesConditions) {
        StringBuilder sql = new StringBuilder("SELECT * FROM " + table_name + " WHERE ");
        int conditionCount = fieldsAndValuesConditions.size();
        int index = 0;

        for (String field : fieldsAndValuesConditions.keySet()) {
            sql.append(field).append(" = ?");
            if (index < conditionCount - 1) {
                sql.append(" AND ");
            }
            index++;
        }

        List<JsonObject> results = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            index = 1;
            for (Object condition : fieldsAndValuesConditions.values()) {
                stmt.setObject(index++, condition);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                int columnCount = rs.getMetaData().getColumnCount();

                while (rs.next()) {
                    JsonObject jsonObject = new JsonObject();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = rs.getMetaData().getColumnName(i);
                        Object value = rs.getObject(i);
                        if (value instanceof Integer) {
                            jsonObject.addProperty(columnName, (Integer) value);
                        } else if (value instanceof String) {
                            jsonObject.addProperty(columnName, (String) value);
                        } else if (value instanceof Double) {
                            jsonObject.addProperty(columnName, (Double) value);
                        } else if (value instanceof Boolean) {
                            jsonObject.addProperty(columnName, (Boolean) value);
                        } else {
                            jsonObject.addProperty(columnName, value != null ? value.toString() : null);
                        }
                    }
                    results.add(jsonObject);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    public static String getDataBasePathFromConfig(){
        Yaml yaml = new Yaml();
        String path = "";
        try (InputStream inputStream = menu.class.getClassLoader().getResourceAsStream("config.yaml")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("file not found! " + "config.yaml");
            } else {
                // Parse the YAML file
                Map<String, Object> config = yaml.load(inputStream);
                // Access the 'path' value
                path = (String) config.get("path");
                return path;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path;
    }
}
