package org.example.service.calculate;

import org.example.model.Discount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.example.model.Order;

import java.time.LocalDateTime;

public class PriceCalculatorTest {
    private final PriceCalculator calculator = new PriceCalculator();

    @Test
    void testRegularCalculate() {
        Order order = new Order(LocalDateTime.parse("2021-02-09T16:00:22"), "Industrial", 8800);
        Discount discount = new Discount(0.5, 0.05);
        double result = calculator.calculate(order, 15, discount, 5);
        Assertions.assertEquals(99000.0, result, 0.001);
    }

}
