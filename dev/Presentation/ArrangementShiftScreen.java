package Presentation;

import Service.ArrangementController;
import Service.ConstraintController;
import Service.WorkerController;

import java.util.List;
import java.util.Scanner;

public class ArrangementShiftScreen {
    ArrangementController ac;
    WorkerController wc;
    ConstraintController cr;

    public ArrangementShiftScreen(ArrangementController ac, WorkerController wc, ConstraintController cr) {
        this.cr = cr;
        this.wc = wc;
        this.ac = ac;
        menu();
    }

    //
    private void menu() {
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        while (choice != 3) {
            System.out.print("Shift " + ac.getCurrentShift().getShiftDate() + "\nChoose 1 of the options:" +
                    "\n1)Choose 2 managers");
            if (ac.getCurrentShift().getShiftManager() == null) System.out.println("(No Managers Chosen Yet");
            System.out.println("\n2)Choose a worker" +
                    "\n3)Exit");
            choice = sc.nextInt();
            switch (choice) {
                case 1: // choose manager.
                    chooseWorker(true);
                    System.out.println(ac.shiftString(ac.getCurrentShift()));
                    break;
                case 2: // choose worker only if there's manager chosen.
                    if (!isThereManager()) {
                        System.out.println("Choose 2 managers at first.");
                        return;
                    }
                    chooseWorker(false);
                    System.out.println(ac.shiftString(ac.getCurrentShift()));
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid choice, Please try again");
                    break;

            }
        }
    }


    private void chooseWorker(boolean isManager) {
        Scanner sc = new Scanner(System.in);
        int choice;
        StringBuilder stringBuilder = new StringBuilder();
        List<String> workersID = ac.getWorkersByConstraints(isManager);
        // Early exit before printing result. Prevent bugs
        if (workersID.isEmpty()) {
            System.out.println("No Workers Found That Gave Constraints In This Shift");
            return;
        }
        int idx = 0;
        for (String id : workersID) {
            // idx =0 show --> 1) ... , idx=1 --->2)... , and so on...
            stringBuilder.append(idx + 1).append(") ").append("Shift Type:").append(ac.getConstraintType(id))
                    .append(", Name: ").append(wc.getWorker(id).getName())
                    .append(", Roles:");
            ac.getRolesString(wc.getWorker(id).getRoles(), stringBuilder);
            stringBuilder.append("\n");
            idx++;
        }
        System.out.println(stringBuilder);
        choice = sc.nextInt();
        // get the worker and put it in the current shift.
        // decreased by 1 - because of idx+1 before.
        if (isManager) ac.getCurrentShift().setShiftManager(wc.getWorker(workersID.get(choice - 1)));
        // Always add the worker to the shift workers list.
        ac.getCurrentShift().addWorker(wc.getWorker(workersID.get(choice - 1)));
    }

    private boolean isThereManager() {

        return ac.getCurrentShift().getShiftManager() != null &&
                ac.getCurrentShift().getShiftManagerEvening() != null;
    }
}
