
import java.io.*;
import java.util.*;

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
    public date createdAt;

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
        // TODO implement here
        return null;
    }

    /**
     * @param product 
     * @return Args: product (Product)
     * Returns: void
     * Description: Supprime un produit du panier
     */
    public void removeProduct(Product product) {
        // TODO implement here
        return null;
    }

    /**
     * Args: -
     * Returns: float - Montant total du panier
     * Description: Calcule la somme des produits x quantités
     * @return
     */
    public float calculateTotal() {
        // TODO implement here
        return 0.0f;
    }

}