package org.example;

import org.example.adapter.HashOrderAdapter;
import org.example.adapter.VerticalOrderAdapter;
import org.example.calculate.PriceCalculator;
import org.example.discount.Discount;
import org.example.discount.DiscountInterface;
import org.example.model.Order;
import org.example.writer.FileResultWriter;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        VerticalOrderAdapter adapter1 = new VerticalOrderAdapter();
        List<Order> orders = new ArrayList<>(adapter1.parse());
        orders.sort(Comparator.comparing(Order::orderDate));
        DiscountInterface discount = new Discount(0.5, 0.05);
        PriceCalculator calculator = new PriceCalculator(10, discount);
        Map<String, Double> uniqueOrders = new HashMap<>();
        int i = 0;
        for (Order order : orders) {
            double oldPrice = uniqueOrders.getOrDefault(order.companyName(), 0.0);
            uniqueOrders.put(order.companyName(), oldPrice + calculator.calculate(order, i));
            i++;
        }
        FileResultWriter writer = new FileResultWriter();
        writer.write(uniqueOrders);
    }
}