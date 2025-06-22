import java.io.*;
import java.util.*;

/**
 * 
 */
public class Payment {

    /**
     * Default constructor
     */
    public Payment() {
    }

    /**
     * 
     */
    public int paymentId;

    /**
     * 
     */
    public float amount;

    /**
     * 
     */
    public String method;

    /**
     * 
     */
    public String status;

    /**
     * 
     */
    public Order order;

    /**
     * @param number 
     * @param expiry 
     * @param cvv 
     * @return Args: number (String), expiry (String), cvv (String)
     * Returns: Boolean - true si les infos carte sont valides
     * Description: Vérifie que les informations de carte sont correctes (validation de base)
     */
    public Boolean validateOrder(String number, String expiry, String cvv) {
        if (number == null || expiry == null || cvv == null) {
            return false;
        }
        // Validation basique
        if (number.length() >= 13 && number.length() <= 19 && 
            expiry.length() >= 5 && cvv.length() >= 3) {
            return true;
        }
        return false;
    }

    /**
     * @return Args: -
     * Returns: Boolean - true si le paiement est réussi
     * Description: Simule le traitement du paiement
     */
    public Boolean processPayment() {
        if (this.amount > 0 && (this.status == null || "pending".equals(this.status))) {
            // Simulation du traitement du paiement
            this.status = "completed";
            return true;
        }
        return false;
    }

    /**
     * @return Args: -
     * Returns: Boolean - true si le remboursement est effectué
     * Description: Gère le remboursement d'un paiement
     */
    public Boolean refund() {
        if ("completed".equals(this.status)) {
            this.status = "refunded";
            return true;
        }
        return false;
    }

}
