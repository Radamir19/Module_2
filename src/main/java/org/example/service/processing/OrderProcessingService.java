package org.example.service.processing;

import org.example.exceptions.OrderParseException;
import org.example.model.Discount;
import org.example.model.Order;
import org.example.reader.HashOrderAdapter;
import org.example.reader.OrderSource;
import org.example.reader.VerticalOrderAdapter;
import org.example.service.calculate.PriceCalculator;
import org.example.writer.FileResultWriter;
import org.example.writer.ResultWriter;

import java.util.*;

public class OrderProcessingService {
    public static void processOrder() {
        Scanner sc = new Scanner(System.in);
        List<OrderSource> adapters = List.of(new VerticalOrderAdapter(), new HashOrderAdapter());
        System.out.print("Please, write path to dfile: ");
        String path = sc.next();
        OrderSource fileAdapter = null;
        for(OrderSource adapter : adapters) {
            if (adapter.canParse(path)) {
                fileAdapter = adapter;
                break;
            }
        }
        if (fileAdapter == null) {
            throw new OrderParseException("Can not parse file. Please send .txt file or without extension file.");
        }
        List<Order> orders = new ArrayList<>(fileAdapter.parse(path));
        orders.sort(Comparator.comparing(Order::orderDate));
        PriceCalculator calculator = new PriceCalculator();
        Discount discount = new Discount(0.5,0.05);
        Map<String, Double> uniqueOrders = new HashMap<>();
        int i = 0;
        for (Order order : orders) {
            double oldPrice = uniqueOrders.getOrDefault(order.companyName(), 0.0);
            uniqueOrders.put(order.companyName(), oldPrice + calculator.calculate(order,10,discount,i));
            i++;
        }
        ResultWriter writer = new FileResultWriter();
        System.out.print("Write path to result file: ");
        String resultPath = sc.next();
        writer.write(uniqueOrders, resultPath);
    }
}
