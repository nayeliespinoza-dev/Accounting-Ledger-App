
package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;

    }

    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public String getDescription() { return description; }
    public String getVendor() { return vendor; }
    public double getAmount() { return amount; }


    public String toString() {
        DateTimeFormatter dateFmt = DateTimeFormatter.ISO_LOCAL_DATE;
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        return String.format("%s %s | %-20s | %-15s | %10.2f",
                dateFmt.format(date),
                timeFmt.format(time),
                truncate(description, 20),
                truncate(vendor, 15),
                amount);
    }

    private String truncate(String s, int max){
        if (s == null) return "";
        s = s.trim();
        if (s.length() <=max) return s;
        return s.substring(0, max -3) + "...";
        }
    }
