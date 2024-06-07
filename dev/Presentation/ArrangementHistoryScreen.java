package Presentation;

import Service.*;

public class ArrangementHistoryScreen {
    ArrangementController ac;

    public ArrangementHistoryScreen(ArrangementController arrangementController) {
        this.ac = arrangementController;
    }

    private void menu() {
        // get all arrangements
        System.out.println("All Shifts History\n-------------------");
        System.out.println(ac.allShiftsHistory());
    }

}
