package Presentation;

import Domain.Worker;

import java.util.Scanner;

public  class ManagerScreen {
    Worker manager;
    // 1 Arrangement screen
    // 2 Arrangements history including all shifts.
    // 3 Constraints history. ( FEATURE)
    //4 RolesScreen
    // 5Create Worker Screen
    // 6Edit Worker Scree
    // 7 Go Back


    public ManagerScreen(Worker manager) {
        this.manager = manager;
        menu();

    }

    private void menu() {
        int choice;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome dear manager. Please choose what you would like to do:" +
                "\n1)Create/Modify Arrangement" +
                "\n2)All Shifts History" +
                "\n3)Constraints History" +
                "\n4)Create/Delete Roles" +
                "\n5)Create New Worker" +
                "\n6)Edit Existing Worker" +
                "\n7)Go Back");
        choice = scanner.nextInt();
        switch (choice) {
            case 1:
                new ArrangementScreen(manager);
                break;
            case 2:
                new ArrangementHistoryScreen();
                break;
            case 3:
                new ConstraintsHistoryScreen();
                break;
            case 4:
                new RoleScreen();
                break;
            case 5:
                new CreateWorkerScreen();
                break;
            case 6:
                new EditWorkerScreen();
                break;
            case 7:
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }
}
