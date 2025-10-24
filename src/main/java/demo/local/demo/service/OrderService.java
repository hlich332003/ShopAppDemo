package demo.local.demo.service;

import demo.local.demo.model.CartItem;
import demo.local.demo.model.Order;
import demo.local.demo.model.OrderItem;
import demo.local.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private Order lastOrder;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(List<CartItem> cartItems) {
        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> items = cartItems.stream().map(item -> {
            OrderItem oi = new OrderItem();
            oi.setProductName(item.getProduct().getName());
            oi.setPrice(item.getProduct().getPrice());
            oi.setQuantity(item.getQuantity());
            return oi;
        }).collect(Collectors.toList());

        order.setItems(items);
        order.setTotal(items.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum());

        return order;
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public void setLastOrder(Order order) {
        this.lastOrder = order;
    }

    public Order getLastOrder() {
        return lastOrder;
    }
}
