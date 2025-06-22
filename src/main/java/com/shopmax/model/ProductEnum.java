package com.shopmax.model;

/**
 * Enum contenant les produits disponibles en dur (pas de base de données)
 */
public enum ProductEnum {
    LAPTOP("Ordinateur Portable", "Laptop haute performance", 999.99f, 10),
    SMARTPHONE("Smartphone", "Téléphone dernière génération", 699.99f, 25),
    HEADPHONES("Casque Audio", "Casque sans fil premium", 199.99f, 50),
    TABLET("Tablette", "Tablette 10 pouces", 349.99f, 15),
    WATCH("Montre Connectée", "Montre intelligente", 299.99f, 30),
    CAMERA("Appareil Photo", "Appareil photo numérique", 549.99f, 8),
    KEYBOARD("Clavier", "Clavier mécanique RGB", 129.99f, 40),
    MOUSE("Souris", "Souris gaming haute précision", 79.99f, 60);

    private final String name;
    private final String description;
    private final float price;
    private final int stockQuantity;

    ProductEnum(String name, String description, float price, int stockQuantity) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public Product toProduct() {
        Product product = new Product();
        product.id = this.ordinal() + 1;
        product.name = this.name;
        product.description = this.description;
        product.price = this.price;
        product.stockQuantity = this.stockQuantity;
        return product;
    }
}
