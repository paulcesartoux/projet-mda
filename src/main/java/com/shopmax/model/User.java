package com.shopmax.model;

public class User {
    public int id;
    public String firstame;
    public String lastName;
    public String email;

    public User() {}

    public Boolean authenticate(String email, String password) {
        return email != null && !email.isEmpty() && password != null && !password.isEmpty();
    }
}
