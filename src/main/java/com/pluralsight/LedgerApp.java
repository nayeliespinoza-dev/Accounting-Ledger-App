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
        System.out.print("Select an option: ");
    }

// Method adding a deposit option

    private void addDeposit() {
        System.out.println("\n--- Add Deposit ---");
        System.out.println("Description: ");
        String description = scanner.nextLine().trim();

        System.out.println("Vendor: ");
        String vendor = scanner.nextLine().trim();

        double amount = readPositiveAmount("Amount (positive): ");

        Transaction t = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, amount);
        transactions.add(t);
        System.out.printf("Deposit of $%.2f added.\n", amount);
    }

// Method for making a payment

    private void makePayment() {
        System.out.println("\n--- Make Payment (Debit)---");
        System.out.print("Description: ");
        String description = scanner.nextLine().trim();

        System.out.println("Vendor: ");
        String vendor = scanner.nextLine().trim();

        double amount = readPositiveAmount("Amount (positive) -> will be recorded as a payment: ");

        Transaction t = new Transaction(LocalDate.now(), LocalTime.now(), description, vendor, -amount);
        transactions.add(t);
        System.out.printf("Payment of $%.2f recorded.\n", amount);
    }

    private double readPositiveAmount(String prompt) {
        double amount = 0.0;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                amount = Double.parseDouble(input);
                if (amount <= 0) {
                    System.out.println("Please enter an amount greater than 0.");
                    continue;
                }
                return amount;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Try again (example: 125.50.");
            }
        }
    }

    private void displayLedger() {
        System.out.println("\n--- Ledger ---");
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }

        System.out.printf("%-20s %-8s | %-20s | %-15s | %10s\n", "Date", "Time", "Description", "Vendor", "Amount");
        System.out.println("-------------------------------------------------------------------------------------------");


        double balance = 0.0;
        for (Transaction t : transactions) {
            System.out.println(t.toString());
            balance += t.getAmount();
        }

        System.out.println("--------------------------------------------------------------------------------------------");
        System.out.printf("Current Balance: $%.2f\n", balance);
        }
    }
