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
            switch (choice.toUpperCase()) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    makePayment();
                    break;
                case "L":
                    showLedgerMenu();
                    break;
                case "X":
                    System.out.println("Exiting out of the app. Have a good day!");
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
        System.out.println("=== Home Screen ===");
        System.out.println("D. Add Deposit");
        System.out.println("P. Make Payment (Debit)");
        System.out.println("L. Display Ledger");
        System.out.println("X. Exit");
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

        System.out.print("Amount (positive): ");
        double amount = Double.parseDouble(scanner.nextLine().trim());

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

    private void showLedgerMenu() {
        boolean inLedgerMenu = true;
        while (inLedgerMenu) {
            System.out.println();
            System.out.println("=== Ledger Screen===");
            System.out.println("A. All");
            System.out.println("D. Deposits");
            System.out.println("P. Payments");
            System.out.println("R. Reports");
            System.out.println("H. Home");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case "A":
                    displayAll();
                    break;
                case "D":
                    displayDeposits();
                    break;
                case "P":
                    displayPayments();
                    break;
                case "R":
                    showReportsMenu();
                    break;
                case "H":
                    inLedgerMenu = false;
                    break;
                default:
                    System.out.println("Invalid option. Please enter A, D, P, or H.");
            }
        }
    }

    private void displayAll() {
        System.out.println("\n--- All Transactions ---");
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded yet.");
            return;
        }

        for (int i = transactions.size() - 1; i >= 0; i--) {
            System.out.println(transactions.get(i));
        }
    }

    private void displayDeposits() {
        System.out.println("\n--- Deposits ---");
        boolean found = false;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getAmount() > 0) {
                System.out.println(t);
                found = true;
            }
        }
        if (!found) System.out.println("No deposits found.");
    }

    private void displayPayments() {
        System.out.println("\n--- Payments ---");
        boolean found = false;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getAmount() < 0) {
                System.out.println(t);
                found = true;
            }
        }
        if (!found) System.out.println("No payments found.");
    }

    private void showReportsMenu() {
        boolean inReportsMenu = true;
        while (inReportsMenu) {
            System.out.println();
            System.out.println("=== Reports menu ===");
            System.out.println("1. Month To Date");
            System.out.println("2. Previous Month");
            System.out.println("3. Year to Date");
            System.out.println("4. Previous Year");
            System.out.println("0. Back");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    showMonthToDate();
                    break;
                case "2":
                    showPreviousMonth();
                    break;
                case "3":
                    showYearToDate();
                    break;
                case "4":
                    showPreviousYear();
                    break;
                case "5":
                    searchByVendor();
                    break;
                case "0":
                    inReportsMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter 1-5 or 0 to go back.");
            }
        }
    }

    private void showMonthToDate(){
        System.out.println("\n--- Month To Date Report ---");
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);

        boolean found = false;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (!t.getDate().isBefore(firstDayOfMonth) && !t.getDate().isAfter(today)){
                System.out.println(t);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No transactions for the current month.");
        }
    }

    private void showPreviousMonth(){
        System.out.println("\n---Previous Month Report---");
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfPreviousMonth = today.minusMonths(1).withDayOfMonth(1);
        LocalDate lastDayOfPreviousMonth = firstDayOfPreviousMonth.withDayOfMonth(firstDayOfPreviousMonth.lengthOfMonth());

        boolean found = false;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            LocalDate date = t.getDate();
            if (!date.isBefore(firstDayOfPreviousMonth) && !date.isAfter(lastDayOfPreviousMonth)) {
                System.out.println(t);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No transactions for the previous month.");
        }
    }

    private void showYearToDate(){
        System.out.println("\n--- Year to Date Report---");
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfYear = today.withDayOfYear(1);

        boolean found = false;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (!t.getDate().isBefore(firstDayOfYear) && !t.getDate().isAfter(today)){
                System.out.println(t);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No transactions for the current year.");
        }
    }

    private void showPreviousYear(){
        System.out.println("\n--- Previous Year Report---");
        LocalDate today = LocalDate.now();
        int previousYear = today.getYear() - 1;

        boolean found = false;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getDate().getYear() == previousYear){
                System.out.println(t);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No transactions for the previous year.");
        }
    }

    private void searchByVendor() {
        System.out.println("\n--- Search by Vendor ---");
        System.out.println("Enter vendor name: ");
        String vendor = scanner.nextLine().trim().toLowerCase();

        boolean found = false;
        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getVendor().toLowerCase().contains(vendor)){
                System.out.println(t);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No transactions found for vendor: " + vendor);
        }
    }
}