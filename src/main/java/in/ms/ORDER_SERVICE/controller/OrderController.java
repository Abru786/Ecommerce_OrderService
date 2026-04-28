package in.ms.ORDER_SERVICE.controller;

import in.ms.ORDER_SERVICE.dto.OrderRequest;
import in.ms.ORDER_SERVICE.entity.Order;
import in.ms.ORDER_SERVICE.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {


    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {

        log.info("Creating order for userId: {}", request.getUserId());
        return ResponseEntity.ok(orderService.createOrder(request));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {

        log.info("Fetching order with id: {}", id);
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    // ✅ Get all orders
    @GetMapping("/all")
    public ResponseEntity<List<Order>> getAllOrders() {
        log.info("Fetching all orders");
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    // ✅ Get orders by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {

        log.info("Fetching orders for userId: {}", userId);
        return ResponseEntity.ok(orderService.getOrdersByUser(userId));
    }
}