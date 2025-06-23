package com.shopmax.model;

import java.util.ArrayList;
import java.util.List;

public class ProductCatalog {
    public List<Product> products;

    public ProductCatalog() {
        this.products = new ArrayList<>();
    }

    public List<Product> sortByPrice(String order) {
        List<Product> sorted = new ArrayList<>(products);
        if ("desc".equalsIgnoreCase(order)) {
            sorted.sort((a, b) -> Float.compare(b.price, a.price));
        } else {
            sorted.sort((a, b) -> Float.compare(a.price, b.price));
        }
        return sorted;
    }

    public List<Product> searchProduct(String keyword) {
        List<Product> result = new ArrayList<>();
        for (Product p : products) {
            if (p.name != null && p.name.toLowerCase().contains(keyword.toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Product> getAllProducts() {
        return this.products;
    }

    // Stub for values() if controller expects ProductCatalog as enum
    public static ProductCatalog[] values() {
        return new ProductCatalog[0];
    }
    // Stub for toProduct() if controller expects it
    public Product toProduct() {
        return null;
    }
    // Stub for ordinal() if controller expects it
    public int ordinal() {
        return 0;
    }
}
