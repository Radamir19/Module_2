package org.example;

import org.example.reader.HashOrderAdapter;
import org.example.reader.OrderSource;
import org.example.reader.VerticalOrderAdapter;
import org.example.service.calculate.PriceCalculator;
import org.example.service.processing.OrderProcessingService;
import org.example.writer.FileResultWriter;
import org.example.writer.ResultWriter;

import java.io.IOException;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {
        ResultWriter writer = new FileResultWriter();
        List<OrderSource> adapters = List.of(new VerticalOrderAdapter(), new HashOrderAdapter());
        PriceCalculator calculator = new PriceCalculator();
        OrderProcessingService processor = new OrderProcessingService(writer, adapters, calculator);
        processor.processOrder();
    }
}