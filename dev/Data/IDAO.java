package Data;

import com.google.gson.JsonObject;

import java.sql.SQLException;
import java.util.Map;

public interface IDAO {
    void update(Map<String, Object> fieldsAndValuesConditions, Map<String, Object> fieldsAndValuesToUpdates);

    void add(JsonObject item_json) throws SQLException;

    void delete(int id);

    JsonObject search(int id) throws SQLException;
}
