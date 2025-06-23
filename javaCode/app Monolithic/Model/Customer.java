
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
     * 
     */
    public ShoppingCart shoppingCart;

    /**
     * 
     */
    public List<Order> orders;



    /**
     * @return Args: number (String), expiry (String), cvv (String)
     * Returns: Boolean - true si les infos carte sont valides
     * Description: Vérifie que les informations de carte sont correctes (validation de base)
     */
    public List<Order> viewOrderHistory() {
        // TODO implement here
        return null;
    }

}