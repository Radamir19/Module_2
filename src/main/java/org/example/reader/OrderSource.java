package org.example.reader;

import org.example.model.Order;

import java.util.List;

public interface OrderSource {
    public List<Order> parse(String path);
    boolean canParse(String path);
}
