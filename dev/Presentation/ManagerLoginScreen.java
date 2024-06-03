package Presentation;

import Domain.Worker;

import java.util.Scanner;

public class ManagerLoginScreen {
    Worker currWorker;
    String password;

    public ManagerLoginScreen(Worker currWorker) {
        this.currWorker = currWorker;
        askForPassword();
    }

    public void askForPassword(){
        Scanner in = new Scanner(System.in);
        System.out.println("Hello "+ currWorker.getName() +"!\nEnter password:");
        password = String.valueOf(in.nextInt());
        // same password as currWorker password?
        if (currWorker.getPassword().equals(password)){
            new ManagerScreen(currWorker);
        }
    }
}
