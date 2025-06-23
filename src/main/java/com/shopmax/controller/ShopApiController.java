package com.shopmax.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/api")
public class ShopApiController {
    private static final List<Map<String, Object>> products = new ArrayList<>();
    private static final List<Map<String, Object>> users = new ArrayList<>();
    private static final Map<Integer, List<Map<String, Object>>> carts = new HashMap<>();
    private static final Map<Integer, Map<String, Object>> orders = new HashMap<>();
    private static int cartIdSeq = 1;
    private static int orderIdSeq = 1;
    private static int paymentIdSeq = 1;

    static {
        products.add(Map.of("id", 1, "name", "Laptop", "description", "A powerful laptop", "price", 1200.0, "stockQuantity", 10));
        products.add(Map.of("id", 2, "name", "Smartphone", "description", "A modern smartphone", "price", 800.0, "stockQuantity", 20));
        products.add(Map.of("id", 3, "name", "Headphones", "description", "Noise-cancelling headphones", "price", 150.0, "stockQuantity", 15));
        users.add(Map.of("id", 1, "email", "john@example.com", "password", "123"));
    }

    @GetMapping("/products")
    public List<Map<String, Object>> getProducts() {
        return products;
    }

    @PostMapping("/cart/add")
    public Map<String, Object> addToCart(@RequestBody Map<String, Object> body) {
        int cartId = body.get("cartId") == null ? cartIdSeq++ : ((Number) body.get("cartId")).intValue();
        int productId = ((Number) body.get("productId")).intValue();
        int quantity = ((Number) body.get("quantity")).intValue();
        Map<String, Object> product = products.stream().filter(p -> ((Number)p.get("id")).intValue() == productId).findFirst().orElse(null);
        if (product == null) return Map.of("error", "Produit non trouvé");
        List<Map<String, Object>> cart = carts.computeIfAbsent(cartId, k -> new ArrayList<>());
        for (int i = 0; i < quantity; i++) cart.add(product);
        return Map.of(
            "cartId", cartId,
            "message", "Produit ajouté au panier",
            "cartTotal", cart.stream().mapToDouble(p -> ((Number)p.get("price")).doubleValue()).sum(),
            "itemsCount", cart.size()
        );
    }

    @GetMapping("/cart/{cartId}")
    public Map<String, Object> getCart(@Parameter(description = "ID du panier", required = true) @PathVariable int cartId) {
        List<Map<String, Object>> cart = carts.get(cartId);
        if (cart == null) return Map.of("error", "Panier non trouvé");
        return Map.of(
            "cartId", cartId,
            "products", cart,
            "total", cart.stream().mapToDouble(p -> ((Number)p.get("price")).doubleValue()).sum(),
            "itemsCount", cart.size()
        );
    }

    @PostMapping("/cart/{cartId}/order")
    public Map<String, Object> createOrder(@PathVariable int cartId) {
        List<Map<String, Object>> cart = carts.get(cartId);
        if (cart == null || cart.isEmpty()) return Map.of("error", "Panier non trouvé ou vide");
        int orderId = orderIdSeq++;
        Map<String, Object> order = new HashMap<>();
        order.put("orderId", orderId);
        order.put("products", new ArrayList<>(cart));
        order.put("status", "confirmed");
        order.put("total", cart.stream().mapToDouble(p -> ((Number)p.get("price")).doubleValue()).sum());
        orders.put(orderId, order);
        cart.clear();
        return Map.of(
            "orderId", orderId,
            "message", "Commande créée avec succès",
            "total", order.get("total"),
            "status", order.get("status")
        );
    }

    @PostMapping("/order/{orderId}/pay")
    public Map<String, Object> payOrder(@PathVariable int orderId, @RequestBody Map<String, Object> paymentData) {
        Map<String, Object> order = orders.get(orderId);
        if (order == null) return Map.of("error", "Commande non trouvée");
        // Simule le paiement
        if (paymentData.get("cardNumber") == null || paymentData.get("expiry") == null || paymentData.get("cvv") == null) {
            return Map.of("error", "Informations de carte invalides");
        }
        order.put("status", "paid");
        return Map.of(
            "paymentId", paymentIdSeq++,
            "message", "Paiement effectué avec succès",
            "amount", order.get("total"),
            "status", order.get("status")
        );
    }

    @PostMapping("/auth/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        return users.stream().anyMatch(u -> u.get("email").equals(credentials.get("email")) && u.get("password").equals(credentials.get("password")))
            ? Map.of("message", "Authentification réussie")
            : Map.of("error", "Identifiants invalides");
    }
}
