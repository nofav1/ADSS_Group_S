package Presentation;

import Service.*;

import java.util.Scanner;

public class ArrangementScreen {
    //Go back
    // Choose 1-6 Shifts and modify them :
    // ShiftArrangementScreen
    private ArrangementController arrangementController;
    private WorkerController workerController;
    private ConstraintController constraintController;


    public ArrangementScreen(ArrangementController arrangementController,WorkerController workerController,ConstraintController constraintController) {
        this.arrangementController = arrangementController;
        this.workerController =workerController; // For history features
        this.constraintController = constraintController;

        menu();
    }

    private void menu() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        // CReates new arrangement if needs.
        String weekDatesString = arrangementController.getWeekDatesString();
        System.out.println("Welcome to ArrangementScreen! Please choose which shift do you want to modify:");
        // INFLATE Menu by Week Dates
        System.out.println(weekDatesString);

        System.out.println("7) Exit");
        choice = scanner.nextInt();
        switch (choice) {
            case 1:
                arrangementController.selectShift(arrangementController.getCurrArrangement().getWeeklyShifts().get(0));
                new ArrangementShiftScreen(arrangementController,workerController,constraintController);
                break;
            case 2:
                arrangementController.selectShift(arrangementController.getCurrArrangement().getWeeklyShifts().get(1));
                new ArrangementShiftScreen(arrangementController,workerController,constraintController);
                break;
            case 3:
                arrangementController.selectShift(arrangementController.getCurrArrangement().getWeeklyShifts().get(2));
                new ArrangementShiftScreen(arrangementController,workerController,constraintController);
                break;
            case 4:
                arrangementController.selectShift(arrangementController.getCurrArrangement().getWeeklyShifts().get(3));
                new ArrangementShiftScreen(arrangementController,workerController,constraintController);
                break;
            case 5:
                arrangementController.selectShift(arrangementController.getCurrArrangement().getWeeklyShifts().get(4));
                new ArrangementShiftScreen(arrangementController,workerController,constraintController);
                break;
            case 6:
                arrangementController.selectShift(arrangementController.getCurrArrangement().getWeeklyShifts().get(5));
                new ArrangementShiftScreen(arrangementController,workerController,constraintController);
                break;
            case 7:
                // Go Back
            default:
                System.out.println("Invalid choice");
                break;
        }
    }
}
