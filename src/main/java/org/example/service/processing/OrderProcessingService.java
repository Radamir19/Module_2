package org.example.service.processing;

import org.example.cli.OrderCliApplication;
import org.example.exceptions.OrderParseException;
import org.example.model.Discount;
import org.example.model.Order;
import org.example.reader.OrderSource;
import org.example.service.calculate.PriceCalculator;
import org.example.writer.ResultWriter;

import java.io.IOException;
import java.util.*;

public class OrderProcessingService {
    private ResultWriter writer;
    private List<OrderSource> adapters;
    private PriceCalculator calculator;
    public OrderProcessingService(ResultWriter writer, List<OrderSource> adapters, PriceCalculator calculator) {
        this.writer = writer;
        this.adapters = adapters;
        this.calculator = calculator;
    }

    public void processOrder() throws IOException {
        String pathToParsingFile = OrderCliApplication.input();
        OrderSource fileAdapter = chooseAdapter(pathToParsingFile);
        List<Order> orders = new ArrayList<>(fileAdapter.parse(pathToParsingFile));
        orders.sort(Comparator.comparing(Order::orderDate));
        Map<String, Double> result = countTotals(orders);
        String pathToResultFile = OrderCliApplication.output();
        writer.write(result, pathToResultFile);
    }

    private Map<String, Double> countTotals(List<Order> orders) {
        Map<String, Double> uniqueOrders = new HashMap<>();
        int i = 0;
        for (Order order : orders) {
            double oldPrice = uniqueOrders.getOrDefault(order.companyName(), 0.0);
            uniqueOrders.put(order.companyName(), oldPrice + calculator.calculate(order, 10, new Discount(0.5, 0.05), i));
            i++;
        }
        return uniqueOrders;
    }

    private OrderSource chooseAdapter(String path) {
        OrderSource fileAdapter = null;
        for (OrderSource adapter : adapters) {
            if (adapter.canParse(path)) {
                fileAdapter = adapter;
                return fileAdapter;
            }
        }
        if (fileAdapter == null) {
            throw new OrderParseException("Can not parse file. Please send .txt file or without extension file.");
        }
        return null;
    }
}
