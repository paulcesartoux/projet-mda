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
    public Date date;

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
     * Description: Permet au client d'annuler une commande si elle n'est pas encore traitée
     */
    public Boolean cancelOrder() {
        if ("pending".equals(this.status) || "confirmed".equals(this.status)) {
            this.status = "cancelled";
            return true;
        }
        return false;
    }

    /**
     * @return Returns: float - Total à payer pour cette commande
     * Description: Calcule le total des articles dans la commande
     */
    public float getTotalAmount() {
        float total = 0.0f;
        if (this.products != null) {
            for (Product product : this.products) {
                total += product.price;
            }
        }
        return total;
    }

    /**
     * @return Args: -
     * Returns: String - résumé de la commande
     * Description: Donne un aperçu des produits et du total
     */
    public String getOrderSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Commande #").append(this.orderId);
        summary.append(" - Statut: ").append(this.status);
        summary.append(" - Total: ").append(getTotalAmount()).append("€");
        if (this.products != null) {
            summary.append(" - ").append(this.products.size()).append(" article(s)");
        }
        return summary.toString();
    }

    /**
     * @param newStatus 
     * @return Args: newStatus (String)
     * Returns: void
     * Description: Modifie le statut de la commande (e.g. 'shipped', 'paid')
     */
    public void updateStatus(String newStatus) {
        if (newStatus != null && !newStatus.trim().isEmpty()) {
            this.status = newStatus;
        }
    }

}
