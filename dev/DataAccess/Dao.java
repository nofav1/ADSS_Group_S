package DataAccess;

import Domain.Worker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface Dao<T> {

    // get all Objects as List
    HashMap<String,T> getAll();

    // get specific object
//    T getObj(String obj);

    // save in DB
    void save(T t);

    // update in DB
    void update(T t);

    // delete in DB
    void delete(T t);
}

