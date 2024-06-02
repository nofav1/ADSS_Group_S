package Service;


import DataAccess.ArrangmentDao;
import DataAccess.Dao;

public class ArrangementController {
    Dao arrangementDao;
    public ArrangementController() {
        arrangementDao = new ArrangmentDao();
    }

}
