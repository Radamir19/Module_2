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
    private Discount discount;
    public OrderProcessingService(ResultWriter writer, List<OrderSource> adapters, PriceCalculator calculator, Discount discount) {
        this.writer = writer;
        this.adapters = adapters;
        this.calculator = calculator;
        this.discount = discount;
    }

    public void processOrder(String inputFile, String outputFile) throws IOException {
        OrderSource fileAdapter = chooseAdapter(inputFile);
        List<Order> orders = new ArrayList<>(fileAdapter.parse(inputFile));
        orders.sort(Comparator.comparing(Order::orderDate));
        Map<String, Double> result = countTotals(orders, discount);
        writer.write(result, outputFile);
    }

    private Map<String, Double> countTotals(List<Order> orders, Discount discount) {
        Map<String, Double> uniqueOrders = new HashMap<>();
        int i = 0;
        for (Order order : orders) {
            double oldPrice = uniqueOrders.getOrDefault(order.companyName(), 0.0);
            uniqueOrders.put(order.companyName(), oldPrice + calculator.calculate(order, 10, discount, i));
            i++;
        }
        return uniqueOrders;
    }

    private OrderSource chooseAdapter(String path) {
        return adapters.stream().filter(adapter -> adapter.canParse(path)).findFirst().orElseThrow(() -> new OrderParseException("Can not parse file. Please send .txt file or without extension file."));
    }
}
