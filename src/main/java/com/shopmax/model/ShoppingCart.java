package com.shopmax.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShoppingCart {
    public int id;
    public Date createdAt;
    public List<Product> products;
    private com.shopmax.model.Customer user;

    public ShoppingCart() {}

    public void addProduct(Product product, int quantity) {
        if (products == null) {
            products = new ArrayList<>();
        }
        for (int i = 0; i < quantity; i++) {
            products.add(product);
        }
    }

    public void removeProduct(Product product) {
        if (products != null) {
            products.remove(product);
        }
    }

    public float calculateTotal() {
        float total = 0.0f;
        if (products != null) {
            for (Product p : products) {
                total += p.price;
            }
        }
        return total;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    public com.shopmax.model.Customer getUser() {
        return user;
    }
    public void setUser(com.shopmax.model.Customer user) {
        this.user = user;
    }
}
