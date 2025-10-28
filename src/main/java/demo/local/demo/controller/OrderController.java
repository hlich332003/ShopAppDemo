package demo.local.demo.controller;

import demo.local.demo.model.CartItem;
import demo.local.demo.model.Order;
import demo.local.demo.service.CartService;
import demo.local.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class OrderController {

    private final CartService cartService;
    private final OrderService orderService;

    @Autowired
    public OrderController(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    // Hiển thị trang nhập thông tin khách hàng
    @GetMapping("/don-hang")
    public String showOrderForm(Model model) {
        List<CartItem> items = cartService.getItems();
        if (items.isEmpty()) {
            model.addAttribute("message", "Giỏ hàng trống.");
            return "gio-hang";
        }

        double total = cartService.getTotal();
        model.addAttribute("items", items);
        model.addAttribute("total", total);
        return "don-hang";
    }

    // Tạo đơn hàng tạm sau khi nhập thông tin khách hàng
    @PostMapping("/xac-nhan-don-hang")
    public String confirmOrder(
        @RequestParam String fullName,
        @RequestParam String phone,
        @RequestParam String email,
        @RequestParam String address,
        @RequestParam String paymentMethod,
        Model model) {
        List<CartItem> items = cartService.getItems();
        if (items.isEmpty()) {
            model.addAttribute("message", "Giỏ hàng trống.");
            return "gio-hang";
        }

        Order order = orderService.createOrder(items);
        order.setCustomerName(fullName);
        order.setPhone(phone);
        order.setEmail(email);
        order.setAddress(address);
        order.setPaymentMethod(paymentMethod);
        order.setCreatedAt(LocalDateTime.now());

        // Lưu đơn hàng tạm để xác nhận sau
        orderService.setLastOrder(order);

        model.addAttribute("order", order);
        return "xac-nhan-don-hang";
    }

    // Xác nhận đơn hàng cuối cùng → lưu vào DB + xóa giỏ hàng
    @PostMapping("/xac-nhan-cuoi")
    public String finalConfirmation(Model model) {
        Order order = orderService.getLastOrder();
        if (order == null) {
            model.addAttribute("message", "Không tìm thấy đơn hàng.");
            return "gio-hang";
        }

        // Lưu đơn hàng và xóa giỏ hàng
        orderService.save(order);
        cartService.clearCart();

        model.addAttribute("order", order);
        model.addAttribute("message", "🎉 Cảm ơn bạn đã đặt hàng!");
        return "xac-nhan-don-hang";
    }
}
