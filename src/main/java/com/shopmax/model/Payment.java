package com.shopmax.model;

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

    // Getters et setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
