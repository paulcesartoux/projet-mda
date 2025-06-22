package com.shopmax.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Admin qui étend User et fournit des fonctionnalités d'administration
 */
public class Admin extends User {

    /**
     * Default constructor
     */
    public Admin() {
    }

    /**
     * @param product 
     * @return Args: product (Product)
     * Returns: Boolean - true si le produit a été ajouté avec succès
     * Description: Ajoute un nouveau produit au catalogue
     */
    public Boolean addProduct(Product product) {
        if (product != null && product.name != null && !product.name.isEmpty()) {
            // Logique pour ajouter le produit au système
            System.out.println("Produit ajouté: " + product.name);
            return true;
        }
        return false;
    }

    /**
     * @param productId 
     * @return Args: productId (int)
     * Returns: Boolean - true si le produit a été supprimé avec succès
     * Description: Supprime un produit du catalogue
     */
    public Boolean removeProduct(int productId) {
        if (productId > 0) {
            // Logique pour supprimer le produit du système
            System.out.println("Produit supprimé: ID " + productId);
            return true;
        }
        return false;
    }

    /**
     * @param product 
     * @return Args: product (Product)
     * Returns: Boolean - true si le produit a été modifié avec succès
     * Description: Met à jour les informations d'un produit existant
     */
    public Boolean updateProduct(Product product) {
        if (product != null && product.id > 0) {
            // Logique pour mettre à jour le produit
            System.out.println("Produit mis à jour: ID " + product.id);
            return true;
        }
        return false;
    }

    /**
     * @return Args: -
     * Returns: List<Order> - Liste de toutes les commandes
     * Description: Récupère toutes les commandes du système
     */
    public List<Order> viewAllOrders() {
        List<Order> orders = new ArrayList<>();
        // Logique pour récupérer toutes les commandes
        return orders;
    }

    /**
     * @param orderId 
     * @param newStatus 
     * @return Args: orderId (int), newStatus (String)
     * Returns: Boolean - true si le statut a été mis à jour avec succès
     * Description: Met à jour le statut d'une commande
     */
    public Boolean updateOrderStatus(int orderId, String newStatus) {
        if (orderId > 0 && newStatus != null && !newStatus.isEmpty()) {
            // Logique pour mettre à jour le statut de la commande
            System.out.println("Statut de la commande " + orderId + " mis à jour: " + newStatus);
            return true;
        }
        return false;
    }

    /**
     * @return Args: -
     * Returns: List<Customer> - Liste de tous les clients
     * Description: Récupère la liste de tous les clients enregistrés
     */
    public List<Customer> viewAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        // Logique pour récupérer tous les clients
        return customers;
    }

}