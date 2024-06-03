package Presentation;

import Domain.Worker;
import Service.*;
import com.sun.tools.javac.Main;

import java.util.Scanner;
import java.util.Scanner;

import java.util.Scanner;

public class MainLoginScreen {
    private Scanner scan = new Scanner(System.in);
    private boolean result = false;
    private WorkerController workerController;

    public MainLoginScreen() {
        Worker currWorker = mainLoginScreen();
        if (currWorker != null) mainScreen(currWorker);
    }


    private Worker mainLoginScreen() {
        Worker mWorker = null;
        WorkerController workerController;
        workerController = new WorkerController();
        System.out.println("Welcome to S Group System. To continue you need to login:\nPlease type your id, or type 0 to exit");
        while (true) {
            int choice = this.scan.nextInt();
            if (choice == 0) {
                System.out.println("Exiting the system. Goodbye!");
                break;
            }

            // Find the worker in the worker's list.
            Worker worker = workerController.getWorker(choice);
            if (worker == null) {
                System.out.println("No worker found. Please try again or type 0 to exit.");
            } else {
                // TODO: Add password feature for every USER (if he has one).
                System.out.println("Successfully logged in! Moving to Main screen");
                mWorker = worker;
                break;
            }
        }
        return mWorker;
    }

    private void mainScreen(Worker currWorker) {
        int choose = -1;
        while (choose != 0) {
            System.out.println("Main Workers Screen.\nPlease Choose An Option:" +
                    "\n1)Show Weekly Arrangement" +
                    "\n2)Show Today's Shift" +
                    "\n3)Constraints Weekly Submission" +
                    "\n4)Manager Screen" +
                    "\n5)Load Fake Data" +
                    "\n5)Exit");
            choose = scan.nextInt();
            switch (choose) {
                case 1:
//                    System.out.println(arrangementController.getCurrArrangement());
                    break;
                case 2:
//                    System.out.println(arrangementController.getCurrArrangement().getTodayShift());
                case 3:
                    new ConstraintsScreen();
                case 4:
                    // Send the user to ManagerLoginScreen only if his Worker is Manager.
                    if (currWorker.isManager()) {
                        new ManagerLoginScreen(currWorker);

                    } else System.out.println("You're not a Manager!");
                    break;
                case 5:
                    // TODO: LOAD FAKE DATA FROM CSV.
                case 0:
                    System.out.println("Exiting.. Good Bye!");
                    break;
                default:
                    System.out.println("Wrong Option!");
                    break;


            }
        }
    }
}