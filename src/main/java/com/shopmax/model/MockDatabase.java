package com.shopmax.model;

import java.util.ArrayList;
import java.util.List;

public class MockDatabase {
    public static List<Product> products = new ArrayList<>();
    public static List<User> users = new ArrayList<>();

    static {
        // Add some products
        Product p1 = new Product();
        p1.id = 1;
        p1.name = "Laptop";
        p1.description = "A powerful laptop";
        p1.price = 1200.0f;
        p1.stockQuantity = 10;
        products.add(p1);

        Product p2 = new Product();
        p2.id = 2;
        p2.name = "Smartphone";
        p2.description = "A modern smartphone";
        p2.price = 800.0f;
        p2.stockQuantity = 20;
        products.add(p2);

        Product p3 = new Product();
        p3.id = 3;
        p3.name = "Headphones";
        p3.description = "Noise-cancelling headphones";
        p3.price = 150.0f;
        p3.stockQuantity = 15;
        products.add(p3);

        // Add one user
        User user = new User();
        user.id = 1;
        user.firstame = "John";
        user.lastName = "Doe";
        user.email = "john@example.com";
        users.add(user);
    }
}
