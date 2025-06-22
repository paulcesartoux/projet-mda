package com.shopmax.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.shopmax.model.Order;
import com.shopmax.model.Payment;
import com.shopmax.model.Product;
import com.shopmax.model.ProductCatalog;
import com.shopmax.model.ShoppingCart;
import com.shopmax.model.UserConstants;

import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * Contrôleur API unique pour gérer le panier, les commandes et les paiements
 */
public class ShopApiController {

    // Stockage en mémoire (simule une base de données)
    private static final Map<Integer, ShoppingCart> carts = new HashMap<>();
    private static final Map<Integer, Order> orders = new HashMap<>();
    private static final Map<Integer, Payment> payments = new HashMap<>();
    
    private static final AtomicInteger cartIdGenerator = new AtomicInteger(1);
    private static final AtomicInteger orderIdGenerator = new AtomicInteger(1);
    private static final AtomicInteger paymentIdGenerator = new AtomicInteger(1);

    public static void main(String[] args) {
        // Démarrage simple de Javalin avec CORS et logs dev
        Javalin app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> cors.add(corsConfig -> corsConfig.anyHost()));
            config.plugins.enableDevLogging();
        }).start(8080);

        // Routes API
        setupRoutes(app);
        
        System.out.println("API démarrée sur http://localhost:8080");
        System.out.println("Documentation Swagger désactivée");
        System.out.println("Endpoints disponibles:");
        System.out.println("- GET /api/products - Liste des produits");
        System.out.println("- POST /api/cart/add - Ajouter au panier");
        System.out.println("- GET /api/cart/{cartId} - Voir le panier");
        System.out.println("- POST /api/cart/{cartId}/order - Commander le panier");
        System.out.println("- POST /api/order/{orderId}/pay - Payer la commande");
    }

    private static void setupRoutes(Javalin app) {
        
        // Route pour lister tous les produits disponibles
        app.get("/api/products", ShopApiController::getProducts);
        
        // Route pour créer un nouveau panier
        app.post("/api/cart/create", ShopApiController::createCart);
        
        // Route pour ajouter un produit au panier
        app.post("/api/cart/add", ShopApiController::addToCart);
        
        // Route pour voir le contenu d'un panier
        app.get("/api/cart/{cartId}", ShopApiController::getCart);
        
        // Route pour passer commande du panier
        app.post("/api/cart/{cartId}/order", ShopApiController::createOrder);
        
        // Route pour payer une commande
        app.post("/api/order/{orderId}/pay", ShopApiController::payOrder);
        
        // Route pour voir une commande
        app.get("/api/order/{orderId}", ShopApiController::getOrder);
    }

    /**
     * GET /api/products - Liste tous les produits disponibles
     */
    private static void getProducts(Context ctx) {
        List<Product> products = new ArrayList<>();
        for (ProductCatalog productEnum : ProductCatalog.values()) {
            products.add(productEnum.toProduct());
        }
        ctx.json(products);
    }

    /**
     * POST /api/cart/create - Crée un nouveau panier
     */
    private static void createCart(Context ctx) {
        ShoppingCart cart = new ShoppingCart();
        cart.setId(cartIdGenerator.getAndIncrement());
        cart.setUser(UserConstants.DEFAULT_CUSTOMER);
        
        carts.put(cart.getId(), cart);
        
        ctx.json(Map.of(
            "cartId", cart.getId(),
            "message", "Panier créé avec succès"
        ));
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

            ShoppingCart cart = carts.get(cartId);
            if (cart == null) {
                ctx.status(404).json(Map.of("error", "Panier non trouvé"));
                return;
            }

            // Trouver le produit par ID
            Product product = findProductById(productId);
            if (product == null) {
                ctx.status(404).json(Map.of("error", "Produit non trouvé"));
                return;
            }

            cart.addProduct(product, quantity);
            
            ctx.json(Map.of(
                "message", "Produit ajouté au panier",
                "cartTotal", cart.calculateTotal(),
                "itemsCount", cart.getProducts().size()
            ));
            
        } catch (Exception e) {
            ctx.status(400).json(Map.of("error", "Données invalides"));
        }
    }

    /**
     * GET /api/cart/{cartId} - Affiche le contenu du panier
     */
    private static void getCart(Context ctx) {
        int cartId = Integer.parseInt(ctx.pathParam("cartId"));
        ShoppingCart cart = carts.get(cartId);
        
        if (cart == null) {
            ctx.status(404).json(Map.of("error", "Panier non trouvé"));
            return;
        }

        ctx.json(Map.of(
            "cartId", cart.getId(),
            "products", cart.getProducts(),
            "total", cart.calculateTotal(),
            "itemsCount", cart.getProducts().size()
        ));
    }

    /**
     * POST /api/cart/{cartId}/order - Transforme le panier en commande
     */
    private static void createOrder(Context ctx) {
        int cartId = Integer.parseInt(ctx.pathParam("cartId"));
        ShoppingCart cart = carts.get(cartId);
        
        if (cart == null) {
            ctx.status(404).json(Map.of("error", "Panier non trouvé"));
            return;
        }

        if (cart.getProducts().isEmpty()) {
            ctx.status(400).json(Map.of("error", "Le panier est vide"));
            return;
        }

        // Créer la commande
        Order order = new Order();
        order.setOrderId(orderIdGenerator.getAndIncrement());
        order.setUser(cart.getUser());
        order.setProducts(new ArrayList<>(cart.getProducts()));
        order.setStatus("confirmed");
        
        orders.put(order.getOrderId(), order);
        
        // Vider le panier
        cart.getProducts().clear();
        
        ctx.json(Map.of(
            "orderId", order.getOrderId(),
            "message", "Commande créée avec succès",
            "total", order.getTotalAmount(),
            "status", order.getStatus()
        ));
    }

    /**
     * POST /api/order/{orderId}/pay - Effectue le paiement d'une commande
     * Body: {"method": "card", "cardNumber": "1234567890123456", "expiry": "12/25", "cvv": "123"}
     */
    private static void payOrder(Context ctx) {
        try {
            int orderId = Integer.parseInt(ctx.pathParam("orderId"));
            Order order = orders.get(orderId);
            
            if (order == null) {
                ctx.status(404).json(Map.of("error", "Commande non trouvée"));
                return;
            }

            Map<String, Object> paymentData = ctx.bodyAsClass(Map.class);
            String method = (String) paymentData.get("method");
            String cardNumber = (String) paymentData.get("cardNumber");
            String expiry = (String) paymentData.get("expiry");
            String cvv = (String) paymentData.get("cvv");

            // Créer le paiement
            Payment payment = new Payment();
            payment.setPaymentId(paymentIdGenerator.getAndIncrement());
            payment.setAmount(order.getTotalAmount());
            payment.setMethod(method);
            payment.setOrder(order);

            // Valider les données de carte
            if (!payment.validateOrder(cardNumber, expiry, cvv)) {
                ctx.status(400).json(Map.of("error", "Informations de carte invalides"));
                return;
            }

            // Traiter le paiement
            if (payment.processPayment()) {
                order.updateStatus("paid");
                payments.put(payment.getPaymentId(), payment);
                
                ctx.json(Map.of(
                    "paymentId", payment.getPaymentId(),
                    "message", "Paiement effectué avec succès",
                    "amount", payment.getAmount(),
                    "status", payment.getStatus()
                ));
            } else {
                ctx.status(500).json(Map.of("error", "Échec du paiement"));
            }
            
        } catch (Exception e) {
            ctx.status(400).json(Map.of("error", "Données de paiement invalides"));
        }
    }

    /**
     * GET /api/order/{orderId} - Affiche les détails d'une commande
     */
    private static void getOrder(Context ctx) {
        int orderId = Integer.parseInt(ctx.pathParam("orderId"));
        Order order = orders.get(orderId);
        
        if (order == null) {
            ctx.status(404).json(Map.of("error", "Commande non trouvée"));
            return;
        }

        ctx.json(Map.of(
            "orderId", order.getOrderId(),
            "status", order.getStatus(),
            "products", order.getProducts(),
            "total", order.getTotalAmount(),
            "summary", order.getOrderSummary(),
            "date", order.getDate()
        ));
    }

    /**
     * Recherche un produit par son ID dans le ProductCatalog
     */
    private static Product findProductById(int id) {
        for (ProductCatalog pc : ProductCatalog.values()) {
            if (pc.ordinal() + 1 == id) {
                return pc.toProduct();
            }
        }
        return null;
    }

} // fin classe
