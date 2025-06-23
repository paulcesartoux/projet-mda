package com.shopmax.model;

import java.util.Date;
import java.util.List;

public class Order {
    public int orderId;
    public Date date;
    public String status;
    public Customer user;
    public List<Product> products;

    public Order() {}

    public Boolean cancelOrder() {
        if ("pending".equalsIgnoreCase(this.status)) {
            this.status = "cancelled";
            return true;
        }
        return false;
    }

    public float getTotalAmount() {
        float total = 0.0f;
        if (products != null) {
            for (Product p : products) {
                total += p.price;
            }
        }
        return total;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public Customer getUser() {
        return user;
    }
    public void setUser(Customer user) {
        this.user = user;
    }
}
