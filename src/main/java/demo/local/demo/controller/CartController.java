package demo.local.demo.controller;

import demo.local.demo.model.CartItem;
import demo.local.demo.model.Product;
import demo.local.demo.repository.ProductRepository;
import demo.local.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gio-hang")
public class CartController {

    private final CartService cartService;
    private final ProductRepository productRepository;

    @Autowired
    public CartController(CartService cartService, ProductRepository productRepository) {
        this.cartService = cartService;
        this.productRepository = productRepository;
    }

    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("items", cartService.getItems());
        model.addAttribute("total", cartService.getTotal());
        return "gio-hang";
    }

    @PostMapping("/them")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
            new IllegalArgumentException("Không tìm thấy sản phẩm với ID: " + productId));
        cartService.addToCart(productId, new CartItem(product, quantity));
        return "redirect:/gio-hang";
    }

    @PostMapping("/xoa")
    public String removeFromCart(@RequestParam Long productId) {
        cartService.removeFromCart(productId);
        return "redirect:/gio-hang";
    }

    @GetMapping("/so-luong")
    @ResponseBody
    public int getCartCount() {
        return cartService.getItems().stream()
            .mapToInt(item -> item.getQuantity())
            .sum();
    }
}

    
