package Presentation;

import Domain.Constraint;
import Domain.Shift;
import Service.*;

import java.util.List;

public class ArrangementShiftScreen {
    Shift currShift;
    RoleController rc;
    ShiftController sc;
    ArrangementController ac;
    WorkerController wc;

    public ArrangementShiftScreen(Shift currShift) {
        this.currShift = currShift;
        rc = new RoleController();
        sc = new ShiftController();
        wc = new WorkerController();
        ac = new ArrangementController();
    }

    //
    private void menu() {
        rc.getRoles();

    }
}
