package com.shopmax.model;

public class Payment {
    public int paymentId;
    public float amount;
    public String method;
    public String status;
    public Order order;

    public Payment() {}

    public Boolean processPayment() {
        if (order != null && amount >= order.getTotalAmount()) {
            this.status = "paid";
            order.updateStatus("paid");
            return true;
        }
        this.status = "failed";
        return false;
    }

    public boolean validateOrder(String number, String expiry, String cvv) {
        // Dummy validation: accept if all fields are non-empty
        return number != null && !number.isEmpty() &&
               expiry != null && !expiry.isEmpty() &&
               cvv != null && !cvv.isEmpty();
    }

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
