
import java.io.*;
import java.util.*;

/**
 * 
 */
public class Customer extends User {

    /**
     * Default constructor
     */
    public Customer() {
    }

    /**
     * 
     */
    public String adress;



    /**
     * @return Args: number (String), expiry (String), cvv (String)
     * Returns: Boolean - true si les infos carte sont valides
     * Description: Vérifie que les informations de carte sont correctes (validation de base)
     */
    public List<Order> viewOrderHistory() {
        List<Order> orderHistory = new ArrayList<>();
        // En réalité, cette méthode devrait récupérer les commandes depuis une base de données
        // Pour l'instant, on retourne une liste vide
        return orderHistory;
    }

    /**
     * @param newAddress 
     * @return Args: newAddress (String)
     * Returns: void
     * Description: Met à jour l'adresse de livraison du client
     */
    public void updateAddress(String newAddress) {
        if (newAddress != null && !newAddress.trim().isEmpty()) {
            this.adress = newAddress;
        }
    }

}