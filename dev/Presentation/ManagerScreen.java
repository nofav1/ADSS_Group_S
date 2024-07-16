package Presentation;

import Service.*;

import java.util.Scanner;

public class ManagerScreen {
    // Data persistence
    private final WorkerController workerController;
    private final ArrangementController arrangementController;
    private final RoleController roleController;
    private final ConstraintController constraintController;
    // 1 Arrangement screen
    // 2 Arrangements history including all shifts.
    // 3 Constraints history. ( FEATURE)
    //4 RolesScreen
    // 5Create Worker Screen
    // 6Edit Worker Scree
    // 7 Go Back


    public ManagerScreen(WorkerController workerController, ArrangementController arrangementController, RoleController roleController, ConstraintController constraintController) {
        this.workerController = workerController;
        this.arrangementController = arrangementController;
        this.roleController = roleController;
        this.constraintController = constraintController;
        // show menu
        menu();

    }

    private void menu() {
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        while (choice != 7) {
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
                    new ArrangementScreen(arrangementController, workerController, constraintController);
                    break;
                case 2:
                    // TODO : CHANGE IT TO MORE INFORMATIVE SCREEN
                    new ArrangementHistoryScreen(arrangementController);
                    break;
                case 3:
                    System.out.println("A Feature that is soon will be developed.");
//                    new ConstraintsHistoryScreen();
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
}
