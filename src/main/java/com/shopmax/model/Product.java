package com.shopmax.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Product {

    /**
     * Default constructor
     */
    public Product() {
    }

    /**
     * 
     */
    public int id;

    /**
     * 
     */
    public String name;

    /**
     * 
     */
    public String description;

    /**
     * 
     */
    public float price;

    /**
     * 
     */
    public int stockQuantity;

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public float getPrice() { return price; }
    public int getStockQuantity() { return stockQuantity; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(float price) { this.price = price; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }



    /**
     * @param keyword 
     * @return Args: keyword (String)
     * Returns: List<Product> - Liste des produits correspondants
     * Description: Recherche les produits par mot-clé dans le nom ou la description
     */
    public List<Product> searchProduct(String keyword) {
        List<Product> results = new ArrayList<>();
        if (keyword != null && !keyword.trim().isEmpty()) {
            String lowerKeyword = keyword.toLowerCase();
            if ((this.name != null && this.name.toLowerCase().contains(lowerKeyword)) ||
                (this.description != null && this.description.toLowerCase().contains(lowerKeyword))) {
                results.add(this);
            }
        }
        return results;
    }

    /**
     * @param category 
     * @return Args: category (String)
     * Returns: List<Product> - Produits filtrés par catégorie
     * Description: Retourne les produits d'une catégorie donnée
     */
    public List<Product> filterByCategory(String category) {
        List<Product> results = new ArrayList<>();
        // Note: Cette implémentation suppose qu'une propriété catégorie sera ajoutée plus tard
        // Pour l'instant, on simule avec le nom du produit
        if (category != null && this.name != null && 
            this.name.toLowerCase().contains(category.toLowerCase())) {
            results.add(this);
        }
        return results;
    }

    /**
     * @param order 
     * @return Args: order (String) - "asc" ou "desc"
     * Returns: List<Product> - Produits triés par prix
     * Description: Trie les produits par prix croissant/décroissant
     */
    public List<Product> sortByPrice(String order) {
        List<Product> results = new ArrayList<>();
        results.add(this);
        // Note: Pour trier plusieurs produits, cette méthode devrait être statique 
        // et prendre une liste en paramètre. Pour l'instant, on retourne le produit actuel.
        return results;
    }

    /**
     * @return Args: -
     * Returns: Boolean - true si le produit est en stock
     * Description: Vérifie la disponibilité (stock > 0)
     */
    public Boolean isAvailable() {
        return this.stockQuantity > 0;
    }

    /**
     * @param quantity 
     * @return Args: quantity (int)
     * Returns: void
     * Description: Met à jour la quantité en stock (par ajout ou retrait)
     */
    public void updateStock(int quantity) {
        this.stockQuantity += quantity;
        if (this.stockQuantity < 0) {
            this.stockQuantity = 0;
        }
    }

}