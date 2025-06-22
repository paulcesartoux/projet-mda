package com.shopmax.model;

/**
 * Enumération des produits disponibles (simule une base de données)
 */
public enum ProductCatalog {
    LAPTOP("Ordinateur Portable", "Ordinateur portable haute performance", 999.99f, 10),
    SMARTPHONE("Smartphone", "Smartphone dernière génération", 699.99f, 25),
    TABLET("Tablette", "Tablette tactile 10 pouces", 299.99f, 15),
    HEADPHONES("Casque Audio", "Casque audio sans fil", 149.99f, 30),
    KEYBOARD("Clavier", "Clavier mécanique gaming", 89.99f, 20),
    MOUSE("Souris", "Souris gaming haute précision", 59.99f, 35),
    MONITOR("Écran", "Écran 24 pouces Full HD", 199.99f, 12),
    WEBCAM("Webcam", "Webcam HD 1080p", 79.99f, 18);

    private final String name;
    private final String description;
    private final float price;
    private final int stock;

    ProductCatalog(String name, String description, float price, int stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Product toProduct() {
        Product product = new Product();
        product.setId(this.ordinal() + 1);
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setStockQuantity(this.stock);
        return product;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public float getPrice() { return price; }
    public int getStock() { return stock; }
}
