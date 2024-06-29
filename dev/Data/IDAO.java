package Data;

import java.util.Map;

public interface IDAO {
    void update(Map<String, Object> fieldsAndValuesConditions, Map<String, Object> fieldsAndValuesToUpdates);

    void add(String recordToAdd);

    void delete(int id);

    String search(int id);
}
