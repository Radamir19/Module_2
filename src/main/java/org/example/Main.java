package org.example;

import org.example.cli.OrderCliApplication;
import org.example.model.Discount;
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
        Discount discount = new Discount(0.5, 0.05);
        OrderProcessingService processor = new OrderProcessingService(writer, adapters, calculator, discount);
        String inputFile = OrderCliApplication.input();
        String outputFile = OrderCliApplication.output();
        processor.processOrder(inputFile, outputFile);
    }
}