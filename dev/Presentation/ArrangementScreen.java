package Presentation;

import Domain.Worker;
import Service.*;
import java.util.Scanner;

public class ArrangementScreen {
    //Go back
    // Choose 1-6 Shifts and modify them :
    // ShiftArrangementScreen
    Worker manager;
    ArrangementController arrangementController;

    public ArrangementScreen(Worker manager) {
        this.manager = manager;
        arrangementController = new ArrangementController();
    }

    private void menu() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to ArrangementScreen! Please choose which shift do you want to modify:" +
                "\n1) Sunday" +
                "\n2) Monday" +
                "\n3) Tuesday" +
                "\n4) Wednesday" +
                "\n5) Thursday" +
                "\n6) Friday" +
                "\n7) Go Back");
        choice = scanner.nextInt();
        switch (choice) {
            case 1:
//                new ArrangementShiftScreen();
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                // Go Back
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

}
