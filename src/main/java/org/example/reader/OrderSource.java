package org.example.reader;

import org.example.model.Order;

import java.io.IOException;
import java.util.List;

public interface OrderSource {
    List<Order> parse(String path) throws IOException;
    boolean canParse(String path);
}
