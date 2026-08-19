package org.example.reader;

import org.example.exceptions.OrderParseException;
import org.example.model.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HashOrderAdapter implements OrderSource {

    private final FileReader reader = new FileReader();

    @Override
    public List<Order> parse(String path) {
        List<Order> orders = new ArrayList<>();
        for (String line : reader.loadLines(path)) {
            String[] parts = line.split("#");
            if (parts.length != 3) {
                throw new OrderParseException("Corrupted file, please fix.");
            }
            LocalDateTime localDateTime = LocalDateTime.parse(parts[0]);
            String companyName = parts[1];
            int numberOfKilograms = Integer.valueOf(parts[2]);
            orders.add(new Order(localDateTime, companyName, numberOfKilograms));
        }
        return orders;
    }

    @Override
    public boolean canParse(String path) {
        return !path.contains(".");
    }
}
