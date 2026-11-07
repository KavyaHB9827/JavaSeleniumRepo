package com.automation.bigbasket.models;

/** One row as displayed on the basket. */
public class BasketItem {
    private final String name;
    private final int quantity;
    private final double unitPrice;

    public BasketItem(String name, int quantity, double unitPrice) {
        this.name = name;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getUnitPrice() { return unitPrice; }
    public double getLineTotal() { return quantity * unitPrice; }

    @Override
    public String toString() {
        return String.format("BasketItem[name=%s, qty=%d, unit=%.2f]", name, quantity, unitPrice);
    }
}
