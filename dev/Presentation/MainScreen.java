package Presentation;

import Domain.Worker;
import Service.*;

import java.util.Scanner;

public class  MainScreen {
    Worker currWorker;
    ArrangementController arrangementController;
    private final Scanner scanner= new Scanner(System.in);

    public MainScreen(Worker currWorker) {
        this.currWorker = currWorker;
        arrangementController = new ArrangementController();
        inflateOptions();
    }

    // Manager Login button
    // Constraints Screen(By Day)
    // Show Sidur
    // Show Today Mishmeret by Morning-Evening
    private void inflateOptions() {
        // input
        Scanner in = new Scanner(System.in);
        int choose = -1;
        // menu

        // get input
        while (choose != 0) {
            System.out.println("Main Workers Screen.\nPlease Choose An Option:" +
                    "\n1)Show Weekly Arrangement" +
                    "\n2)Show Today's Shift" +
                    "\n3)Constraints Weekly Submission" +
                    "\n4)Manager Screen" +
                    "\n5)Load Fake Data" +
                    "\n5)Exit");
            choose = in.nextInt();
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
                        routeToMainScreen(currWorker);

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

    private void routeToMainScreen(Worker worker) {
        // Logic to route to the main screen
        System.out.println("Manager Login");
        // Placeholder for main screen logic
        new Thread(() -> new ManagerLoginScreen(worker)).start();

    }

}
