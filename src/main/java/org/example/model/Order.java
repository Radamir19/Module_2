package org.example.model;

import java.time.LocalDateTime;

public record Order(LocalDateTime orderDate, String companyName, int numberOfKilograms) {
    public Order {
        if (orderDate == null) {
            throw new IllegalArgumentException("Date must be set.");
        } else if (companyName == null) {
            throw new IllegalArgumentException("Company name must be set.");
        } else if (numberOfKilograms < 0) {
            throw new IllegalArgumentException("Number of kilograms can not be negative.");
        }
    }

    @Override
    public String toString() {
        return "order date: " + orderDate()
                + "\ncompany name: " + companyName()
                + "\nnumber of kilograms: " + numberOfKilograms() + "\n";
    }
}