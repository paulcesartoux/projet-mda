package com.shopmax.model;

public class Product {
    public int id;
    public String name;
    public String description;
    public float price;
    public int stockQuantity;

    public Product() {}

    public Boolean isAvailable() {
        return this.stockQuantity > 0;
    }

    public void updateStock(int quantity) {
        this.stockQuantity += quantity;
    }
}
