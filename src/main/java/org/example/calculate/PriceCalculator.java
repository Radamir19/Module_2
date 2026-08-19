package org.example.calculate;

import org.example.discount.DiscountInterface;
import org.example.model.Order;

public class PriceCalculator {
    private final double priceForKilogram;

    DiscountInterface discount;

    public PriceCalculator(double priceForKilogram, DiscountInterface discount) {
        if (priceForKilogram < 0) {
            throw new IllegalArgumentException("Price can not be negative.");
        } else if (discount == null) {
            throw new IllegalArgumentException("Please, set a discount.");
        }
        this.priceForKilogram = priceForKilogram;
        this.discount = discount;
    }

    public double getPriceForKilogram() {
        return priceForKilogram;
    }

    public double calculate(Order order, int index) {
        return (order.numberOfKilograms() * priceForKilogram * (1 - discount.discountForTheOrder(index)));
    }
}
