package com.shopmax.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 */
public class ShoppingCart {

    /**
     * Default constructor
     */
    public ShoppingCart() {
    }

    /**
     * 
     */
    public int id;

    /**
     * 
     */
    public Date createdAt;

    /**
     * 
     */
    public Customer user;

    /**
     * 
     */
    public List<Product> products;



    /**
     * @param product 
     * @param quantity 
     * @return Args: product (Product), quantity (int)
     * Returns: void
     * Description: Ajoute un produit au panier
     */
    public void addProduct(Product product, int quantity) {
        if (product != null && quantity > 0) {
            if (this.products == null) {
                this.products = new ArrayList<>();
            }
            // Ajouter le produit quantity fois
            for (int i = 0; i < quantity; i++) {
                this.products.add(product);
            }
        }
    }

    /**
     * @param product 
     * @return Args: product (Product)
     * Returns: void
     * Description: Supprime un produit du panier
     */
    public void removeProduct(Product product) {
        if (product != null && this.products != null) {
            this.products.remove(product);
        }
    }

    /**
     * Args: -
     * Returns: float - Montant total du panier
     * Description: Calcule la somme des produits x quantités
     * @return
     */
    public float calculateTotal() {
        float total = 0.0f;
        if (this.products != null) {
            for (Product product : this.products) {
                total += product.price;
            }
        }
        return total;
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

}