package com.automation.bigbasket.models;

public class ProductDetails {
    private String name;
    private double price;       // selling price
    private double mrp;         // original price (== price when no discount)
    private String discount;    // e.g. "10% OFF" or "N/A"
    private String packSize;    // e.g. "500 ml"
    private int quantity;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public double getMrp() { return mrp; }
    public void setMrp(double mrp) { this.mrp = mrp; }
    public String getDiscount() { return discount; }
    public void setDiscount(String discount) { this.discount = discount; }
    public String getPackSize() { return packSize; }
    public void setPackSize(String packSize) { this.packSize = packSize; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    @Override
    public String toString() {
        return String.format("Product[name=%s, price=%.2f, mrp=%.2f, discount=%s, pack=%s, qty=%d]",
                name, price, mrp, discount, packSize, quantity);
    }
}
