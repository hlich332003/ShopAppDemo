package demo.local.demo.service;

import demo.local.demo.model.CartItem;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {
    private final Map<Long, CartItem> cartItems = new HashMap<>();

    public void addToCart(Long productId, CartItem item) {
        if (cartItems.containsKey(productId)) {
            CartItem existing = cartItems.get(productId);
            existing.setQuantity(existing.getQuantity() + item.getQuantity());
        } else {
            cartItems.put(productId, item);
        }
    }

    public void removeFromCart(Long productId) {
        cartItems.remove(productId);
    }

    public List<CartItem> getItems() {
        return new ArrayList<>(cartItems.values());
    }

    public void clearCart() {
        cartItems.clear();
    }

    public double getTotal() {
        return cartItems.values().stream()
            .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
            .sum();
    }
}
