package com.shopmax.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * Contrôleur API simple avec Javalin (sans dépendances sur les classes model existantes)
 */
public class SimpleShopController {

    // Stockage en mémoire simple
    private static final Map<Integer, Map<String, Object>> carts = new HashMap<>();
    private static final Map<Integer, Map<String, Object>> orders = new HashMap<>();
    private static int cartIdCounter = 1;
    private static int orderIdCounter = 1;

    // Catalogue de produits hardcodé
    private static final List<Map<String, Object>> PRODUCTS = Arrays.asList(
        createProduct(1, "Ordinateur Portable", "Ordinateur portable haute performance", 999.99f, 10),
        createProduct(2, "Smartphone", "Smartphone dernière génération", 699.99f, 25),
        createProduct(3, "Tablette", "Tablette tactile 10 pouces", 299.99f, 15),
        createProduct(4, "Casque Audio", "Casque audio sans fil", 149.99f, 30),
        createProduct(5, "Clavier", "Clavier mécanique gaming", 89.99f, 20),
        createProduct(6, "Souris", "Souris gaming haute précision", 59.99f, 35)
    );

    // Utilisateur hardcodé
    private static final Map<String, Object> DEFAULT_USER = Map.of(
        "id", 1,
        "firstName", "John",
        "lastName", "Doe",
        "email", "john.doe@example.com",
        "address", "123 Rue de la Paix, 75001 Paris"
    );    public static void main(String[] args) {
        Javalin app = Javalin.create().start(8080);

        setupRoutes(app);
        
        System.out.println("🚀 API ShopMax démarrée sur http://localhost:8080");
        System.out.println("📋 Endpoints disponibles:");
        System.out.println("   GET    /api/products           - Liste des produits");
        System.out.println("   POST   /api/cart/create        - Créer un panier");
        System.out.println("   POST   /api/cart/add           - Ajouter au panier");
        System.out.println("   GET    /api/cart/{cartId}      - Voir le panier");
        System.out.println("   POST   /api/cart/{cartId}/order - Commander le panier");
        System.out.println("   POST   /api/order/{orderId}/pay - Payer la commande");
        System.out.println("   GET    /api/order/{orderId}    - Voir la commande");
    }

    private static void setupRoutes(Javalin app) {
        // CORS et headers
        app.before(ctx -> {
            ctx.header("Access-Control-Allow-Origin", "*");
            ctx.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            ctx.header("Access-Control-Allow-Headers", "Content-Type, Authorization");
        });

        app.options("/*", ctx -> ctx.status(200));

        // Routes principales
        app.get("/api/products", SimpleShopController::getProducts);
        app.post("/api/cart/create", SimpleShopController::createCart);
        app.post("/api/cart/add", SimpleShopController::addToCart);
        app.get("/api/cart/{cartId}", SimpleShopController::getCart);
        app.post("/api/cart/{cartId}/order", SimpleShopController::createOrder);
        app.post("/api/order/{orderId}/pay", SimpleShopController::payOrder);
        app.get("/api/order/{orderId}", SimpleShopController::getOrder);

        // Route de test
        app.get("/", ctx -> ctx.json(Map.of("message", "ShopMax API is running!", "endpoints", "/api/products")));
    }

    /**
     * GET /api/products - Liste tous les produits
     */
    private static void getProducts(Context ctx) {
        ctx.json(Map.of("products", PRODUCTS));
    }

    /**
     * POST /api/cart/create - Crée un nouveau panier
     */
    private static void createCart(Context ctx) {
        int cartId = cartIdCounter++;
        Map<String, Object> cart = Map.of(
            "id", cartId,
            "user", DEFAULT_USER,
            "products", new ArrayList<>(),
            "createdAt", new Date().toString()
        );
        
        carts.put(cartId, new HashMap<>(cart));
        ctx.json(Map.of("cartId", cartId, "message", "Panier créé avec succès"));
    }

    /**
     * POST /api/cart/add - Ajoute un produit au panier
     * Body: {"cartId": 1, "productId": 1, "quantity": 2}
     */
    private static void addToCart(Context ctx) {
        try {
            Map<String, Object> body = ctx.bodyAsClass(Map.class);
            int cartId = ((Number) body.get("cartId")).intValue();
            int productId = ((Number) body.get("productId")).intValue();
            int quantity = ((Number) body.get("quantity")).intValue();

            Map<String, Object> cart = carts.get(cartId);
            if (cart == null) {
                ctx.status(404).json(Map.of("error", "Panier non trouvé"));
                return;
            }

            Map<String, Object> product = findProductById(productId);
            if (product == null) {
                ctx.status(404).json(Map.of("error", "Produit non trouvé"));
                return;
            }

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> products = (List<Map<String, Object>>) cart.get("products");
            
            // Ajouter le produit quantity fois
            for (int i = 0; i < quantity; i++) {
                products.add(new HashMap<>(product));
            }

            float total = calculateCartTotal(products);
            
            ctx.json(Map.of(
                "message", "Produit ajouté au panier",
                "cartTotal", total,
                "itemsCount", products.size()
            ));
            
        } catch (Exception e) {
            ctx.status(400).json(Map.of("error", "Données invalides: " + e.getMessage()));
        }
    }

    /**
     * GET /api/cart/{cartId} - Affiche le contenu du panier
     */
    private static void getCart(Context ctx) {
        try {
            int cartId = Integer.parseInt(ctx.pathParam("cartId"));
            Map<String, Object> cart = carts.get(cartId);
            
            if (cart == null) {
                ctx.status(404).json(Map.of("error", "Panier non trouvé"));
                return;
            }

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> products = (List<Map<String, Object>>) cart.get("products");
            float total = calculateCartTotal(products);

            ctx.json(Map.of(
                "cartId", cartId,
                "products", products,
                "total", total,
                "itemsCount", products.size()
            ));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "ID de panier invalide"));
        }
    }

    /**
     * POST /api/cart/{cartId}/order - Transforme le panier en commande
     */
    private static void createOrder(Context ctx) {
        try {
            int cartId = Integer.parseInt(ctx.pathParam("cartId"));
            Map<String, Object> cart = carts.get(cartId);
            
            if (cart == null) {
                ctx.status(404).json(Map.of("error", "Panier non trouvé"));
                return;
            }

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> products = (List<Map<String, Object>>) cart.get("products");
            
            if (products.isEmpty()) {
                ctx.status(400).json(Map.of("error", "Le panier est vide"));
                return;
            }

            int orderId = orderIdCounter++;
            float total = calculateCartTotal(products);
            
            Map<String, Object> order = Map.of(
                "orderId", orderId,
                "user", cart.get("user"),
                "products", new ArrayList<>(products),
                "total", total,
                "status", "confirmed",
                "date", new Date().toString()
            );
            
            orders.put(orderId, new HashMap<>(order));
            
            // Vider le panier
            products.clear();
            
            ctx.json(Map.of(
                "orderId", orderId,
                "message", "Commande créée avec succès",
                "total", total,
                "status", "confirmed"
            ));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "ID de panier invalide"));
        }
    }

    /**
     * POST /api/order/{orderId}/pay - Effectue le paiement d'une commande
     * Body: {"method": "card", "cardNumber": "1234567890123456", "expiry": "12/25", "cvv": "123"}
     */
    private static void payOrder(Context ctx) {
        try {
            int orderId = Integer.parseInt(ctx.pathParam("orderId"));
            Map<String, Object> order = orders.get(orderId);
            
            if (order == null) {
                ctx.status(404).json(Map.of("error", "Commande non trouvée"));
                return;
            }

            Map<String, Object> paymentData = ctx.bodyAsClass(Map.class);
            String method = (String) paymentData.get("method");
            String cardNumber = (String) paymentData.get("cardNumber");
            String expiry = (String) paymentData.get("expiry");
            String cvv = (String) paymentData.get("cvv");

            // Validation basique des données de carte
            if (!isValidCard(cardNumber, expiry, cvv)) {
                ctx.status(400).json(Map.of("error", "Informations de carte invalides"));
                return;
            }

            // Simuler le traitement du paiement
            order.put("status", "paid");
            order.put("paymentMethod", method);
            order.put("paymentDate", new Date().toString());
            
            ctx.json(Map.of(
                "message", "Paiement effectué avec succès",
                "orderId", orderId,
                "amount", order.get("total"),
                "status", "paid"
            ));
            
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "ID de commande invalide"));
        } catch (Exception e) {
            ctx.status(400).json(Map.of("error", "Données de paiement invalides"));
        }
    }

    /**
     * GET /api/order/{orderId} - Affiche les détails d'une commande
     */
    private static void getOrder(Context ctx) {
        try {
            int orderId = Integer.parseInt(ctx.pathParam("orderId"));
            Map<String, Object> order = orders.get(orderId);
            
            if (order == null) {
                ctx.status(404).json(Map.of("error", "Commande non trouvée"));
                return;
            }

            ctx.json(order);
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "ID de commande invalide"));
        }
    }

    // Méthodes utilitaires
    private static Map<String, Object> createProduct(int id, String name, String description, float price, int stock) {
        return Map.of(
            "id", id,
            "name", name,
            "description", description,
            "price", price,
            "stockQuantity", stock,
            "available", stock > 0
        );
    }

    private static Map<String, Object> findProductById(int productId) {
        return PRODUCTS.stream()
            .filter(p -> ((Integer) p.get("id")).equals(productId))
            .findFirst()
            .orElse(null);
    }

    private static float calculateCartTotal(List<Map<String, Object>> products) {
        return (float) products.stream()
            .mapToDouble(p -> ((Number) p.get("price")).doubleValue())
            .sum();
    }

    private static boolean isValidCard(String cardNumber, String expiry, String cvv) {
        return cardNumber != null && cardNumber.length() >= 13 && cardNumber.length() <= 19 &&
               expiry != null && expiry.length() >= 5 &&
               cvv != null && cvv.length() >= 3;
    }
}
