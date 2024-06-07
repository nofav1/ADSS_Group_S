package Presentation;

import Service.*;

import java.util.Scanner;

public class ConstraintsScreen {


    private ArrangementController arrangementController;
    private WorkerController workerController;
    private ConstraintController constraintController;


    // Constructor
    public ConstraintsScreen(ArrangementController arrangementController, WorkerController workerController, ConstraintController constraintController) {
        this.arrangementController = arrangementController;
        this.workerController = workerController;
        this.constraintController = constraintController;
        menu();
    }


    private void menu() {
        Scanner sc = new Scanner(System.in);
        int choice = -1;
        String weekDatesString = arrangementController.getWeekDatesString();
        while (choice != 7) {
            System.out.println("Welcome to the Constraints Screen\n-------------------");
            System.out.println(weekDatesString);
            System.out.println("7) Exit");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    arrangementController.selectShift(arrangementController.getCurrArrangement().getWeeklyShifts().get(0));
                    new ConstraintsShiftScreen(arrangementController,workerController,constraintController);
                    break;
                case 2:
                    arrangementController.selectShift(arrangementController.getCurrArrangement().getWeeklyShifts().get(1));
                    new ConstraintsShiftScreen(arrangementController,workerController,constraintController);

                    break;
                case 3:
                    arrangementController.selectShift(arrangementController.getCurrArrangement().getWeeklyShifts().get(2));
                    new ConstraintsShiftScreen(arrangementController,workerController,constraintController);

                    break;
                case 4:
                    arrangementController.selectShift(arrangementController.getCurrArrangement().getWeeklyShifts().get(3));
                    new ConstraintsShiftScreen(arrangementController,workerController,constraintController);

                    break;
                case 5:
                    arrangementController.selectShift(arrangementController.getCurrArrangement().getWeeklyShifts().get(4));
                    new ConstraintsShiftScreen(arrangementController,workerController,constraintController);

                    break;
                case 6:
                    arrangementController.selectShift(arrangementController.getCurrArrangement().getWeeklyShifts().get(5));
                    new ConstraintsShiftScreen(arrangementController,workerController,constraintController);


                    break;
                case 7:
                    break;
                default:
                    System.out.println("Invalid Option. Try again");
            }
        }

    }
}
