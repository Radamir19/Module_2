package org.example.discount;

public class Discount implements DiscountInterface {
    private final double startDiscount;
    private final double discountStep;

    public Discount(double startDiscount, double discountStep) {
        if (startDiscount < 0) {
            throw new IllegalArgumentException("Discount can not be negative.");
        } else if (discountStep < 0) {
            throw new IllegalArgumentException("Discount step can not be negative.");
        }
        this.startDiscount = startDiscount;
        this.discountStep = discountStep;
    }

    public double getStartDiscount() {
        return startDiscount;
    }

    public double getDiscountStep() {
        return discountStep;
    }

    @Override
    public double discountForTheOrder(int index) {
        return Math.max(0, startDiscount - (discountStep * index));
    }
}
