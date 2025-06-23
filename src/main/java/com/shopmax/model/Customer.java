package com.shopmax.model;

import java.util.List;

public class Customer extends User {
    public String adress;
    public ShoppingCart shoppingCart;
    public List<Order> orders;

    public Customer() {}

    public List<Order> viewOrderHistory() {
        return orders;
    }
}
