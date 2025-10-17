package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class LedgerApp {
    private ArrayList<Transaction> transactions = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        LedgerApp app = new LedgerApp();
        app.run();
    }

    public void run() {
        boolean running = true;
        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1":
                    addDeposit();
                    break;
                case "2":
                    makePayment();
                    break;
                case "3":
                    displayLedger();
                    break;
                case "4":
                    System.out.println("Exiting. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid selection. Please enter 1-4.");

            }
        }
        scanner.close();
    }

    private void showMenu() {
        System.out.println();
        System.out.println("    Accounting Ledger    ");
        System.out.println("1. Add Deposit");
        System.out.println("2. Make Payment (Debit)");
        System.out.println("3. Display Ledger");
        System.out.println("4. Exit");
        System.out.println("Select an option: ");
    }

    private void addDeposit() {}

    private void makePayment() {}

    private void displayLedger() {}

}
