package Presentation;

import Service.ArrangementController;
import Service.ConstraintController;
import Service.WorkerController;

import java.util.Scanner;

public class ConstraintsShiftScreen {
    ArrangementController ac;
    WorkerController wc;
    ConstraintController cr;

    public ConstraintsShiftScreen(ArrangementController ac, WorkerController wc, ConstraintController cr) {
        this.cr = cr;
        this.wc = wc;
        this.ac = ac;
        menu();
    }

    //
    private void menu() {
        Scanner sc = new Scanner(System.in);
        String shiftDate = ac.getCurrentShift().getShiftDate();
        int choice;
        System.out.println("Shift " + ac.getCurrentShift().getShiftDate() +
                "\n Please choose the time you can work:" +
                "\n1)Morning" +
                "\n2)Evening" +
                "\n3)Double" +
                "\n4)Exit");
        choice = sc.nextInt();
        switch (choice) {
            case 1:
                ac.getCurrentShift().addConstraint(cr.createConstraint(wc.getCurrWorker(), "Morning", shiftDate));
                break;
            case 2:
                ac.getCurrentShift().addConstraint(cr.createConstraint(wc.getCurrWorker(), "Evening", shiftDate));
                break;
            case 3:
                ac.getCurrentShift().addConstraint(cr.createConstraint(wc.getCurrWorker(), "Double", shiftDate));
                break;
            case 4:
                break;
            default:
                System.out.println("Invalid choice, Please try again");

        }

    }

}
