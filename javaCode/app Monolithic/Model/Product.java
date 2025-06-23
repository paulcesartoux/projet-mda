
import java.io.*;
import java.util.*;

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




    /**
     * @return Args: -
     * Returns: Boolean - true si le produit est en stock
     * Description: Vérifie la disponibilité (stock > 0)
     */
    public Boolean isAvailable() {
        // TODO implement here
        return null;
    }

    /**
     * @param quantity 
     * @return Args: quantity (int)
     * Returns: void
     * Description: Met à jour la quantité en stock (par ajout ou retrait)
     */
    public void updateStock(int quantity) {
        // TODO implement here
        return null;
    }

}