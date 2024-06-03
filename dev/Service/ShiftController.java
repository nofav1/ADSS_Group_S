package Service;

import DataAccess.Dao;
import DataAccess.ShiftDao;
import Domain.Shift;
import Domain.SystemDate;

import java.text.ParseException;

public class ShiftController {
    Dao shiftDao;

    public ShiftController() {
        shiftDao = ShiftDao.getInstance();
    }

    public void createShift(java.lang.String date) throws ParseException {
        new SystemDate(date);
        Shift newShift = null;
        newShift = new Shift(date);
        shiftDao.save(newShift);
    }

        public void updateShift (Shift shift){
            shiftDao.update(shift);
        }


    }
