package com.shopmax.model;

/**
 * Constantes pour l'utilisateur hardcodé
 */
public class UserConstants {
    
    public static final Customer DEFAULT_CUSTOMER = createDefaultCustomer();
    
    private static Customer createDefaultCustomer() {
        Customer customer = new Customer();
        customer.id = 1;
        customer.firstame = "John";
        customer.lastName = "Doe";
        customer.email = "john.doe@example.com";
        customer.setAdress("123 Rue de la Paix, 75001 Paris");
        return customer;
    }
}
