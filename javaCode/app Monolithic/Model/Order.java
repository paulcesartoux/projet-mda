
import java.io.*;
import java.util.*;

/**
 * 
 */
public class Order {

    /**
     * Default constructor
     */
    public Order() {
    }

    /**
     * 
     */
    public int orderId;

    /**
     * 
     */
    public date date;

    /**
     * 
     */
    public String status;

    /**
     * 
     */
    public Customer user;

    /**
     * 
     */
    public List<Product> products;




    /**
     * @return Args: -
     * Returns: Boolean - true si la commande a été annulée avec succès
     * Description: Permet au client d’annuler une commande si elle n’est pas encore traitée
     */
    public Boolean cancelOrder() {
        // TODO implement here
        return null;
    }

    /**
     * @return Returns: float - Total à payer pour cette commande
     * Description: Calcule le total des articles dans la commande
     */
    public float getTotalAmount() {
        // TODO implement here
        return 0.0f;
    }

    /**
     * @param newStatus 
     * @return Args: newStatus (String)
     * Returns: void
     * Description: Modifie le statut de la commande (e.g. 'shipped', 'paid')
     */
    public void updateStatus(String newStatus) {
        // TODO implement here
        return null;
    }

}