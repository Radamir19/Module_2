package org.example.service.calculate;

import org.example.model.Discount;
import org.example.model.Order;

public class PriceCalculator {

    public double calculate(Order order, double priceForKilogram, Discount discount, int index) {
        return (order.numberOfKilograms() * priceForKilogram * (1 - Math.max(0, discount.getStartDiscount() - discount.getDiscountStep() * index)));
    }
}
