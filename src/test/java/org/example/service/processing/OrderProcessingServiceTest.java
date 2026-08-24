package org.example.service.processing;

import org.example.exceptions.OrderParseException;
import org.example.model.Discount;
import org.example.model.Order;
import org.example.reader.OrderSource;
import org.example.service.calculate.PriceCalculator;
import org.example.writer.ResultWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderProcessingServiceTest {
    @Mock
    ResultWriter writer;
    @Mock
    OrderSource hashAdapter;
    @Mock
    OrderSource verticalAdapter;
    @Mock
    PriceCalculator calculator;
    private Discount discount;
    private OrderProcessingService service;

    @BeforeEach
    void generateServiceConstructor() {
        discount = new Discount(0.5, 0.0);
        service = new OrderProcessingService(writer, List.of(hashAdapter, verticalAdapter), calculator, discount);
    }

    private List<Order> sampleOrders() {
        return List.of(
                new Order(LocalDateTime.parse("2021-02-09T16:00:22"), "Industrial", 8800),
                new Order(LocalDateTime.parse("2021-02-09T10:48:34"), "Mosque", 33120)
        );
    }

    @Test
    void testRegularProcessingForTxt() throws IOException {
        String inputPath = "service.txt";
        String outputPath = "service_txt_result.txt";

        when(verticalAdapter.canParse(inputPath)).thenReturn(true);
        when(verticalAdapter.parse(inputPath)).thenReturn(sampleOrders());
        List<Order> orders = sampleOrders();
        when(calculator.calculate(eq(orders.get(0)), anyDouble(), any(Discount.class), anyInt())).thenReturn(100d);
        when(calculator.calculate(eq(orders.get(1)), anyDouble(), any(Discount.class), anyInt())).thenReturn(200d);
        service.processOrder(inputPath, outputPath);
        Map<String, Double> expectedMap = Map.of("Mosque", 200d, "Industrial", 100d);

        verify(writer).write(expectedMap, outputPath);
    }

    @Test
    void testAllAdaptersAreUseless() throws IOException {
        String inputPath = "service.pdf";
        String outputPath = "service_txt_result.txt";
        when(hashAdapter.canParse(inputPath)).thenReturn(false);
        when(verticalAdapter.canParse(inputPath)).thenReturn(false);
        Assertions.assertThrows(OrderParseException.class, () -> service.processOrder(inputPath, outputPath));
        verify(writer, never()).write(any(), any());
    }

    @Test
    void testIOExceptionThrowsAfterAllActions() throws IOException {
        String inputPath = "service";
        String outputPath = "service.txt";

        when(hashAdapter.canParse(inputPath)).thenReturn(true);
        when(hashAdapter.parse(inputPath)).thenReturn(sampleOrders());
        doThrow(new IOException()).when(writer).write(any(), any());
        Assertions.assertThrows(IOException.class, () -> service.processOrder(inputPath, outputPath));
    }
}