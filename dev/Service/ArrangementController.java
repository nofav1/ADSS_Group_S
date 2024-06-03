package Service;


import DataAccess.ArrangementDao;
import DataAccess.Dao;

public class ArrangementController {
    Dao arrangementDao;

    public ArrangementController() {
        arrangementDao = ArrangementDao.getInstance();
    }

/*    public getCurrentArrangement(){
        int i = arrangementDao.getAll().size() - 1;
        return arrangementDao.getAll().get(i); // get last arrangement
    }*/



}
