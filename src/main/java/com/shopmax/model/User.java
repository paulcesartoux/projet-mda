package com.shopmax.model;

/**
 * 
 */
public class User {

    /**
     * Default constructor
     */
    public User() {
    }

    /**
     * 
     */
    public int id;

    /**
     * 
     */
    public String firstame;

    /**
     * 
     */
    public String lastName;

    /**
     * 
     */
    public String email;

    /**
     * @param email 
     * @param password 
     * @return Args: email (String), password (String)
     * Returns: Boolean - true si les identifiants sont valides
     * Description: Vérifie l'identité de l'utilisateur pour la connexion
     */
    public Boolean authenticate(String email, String password) {
        if (email == null || password == null) {
            return false;
        }
        if (this.email != null && this.email.equals(email) && password.length() >= 6) {
            return true;
        }
        return false;
    }

}