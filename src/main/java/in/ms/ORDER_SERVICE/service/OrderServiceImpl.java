package in.ms.ORDER_SERVICE.service;


import in.ms.ORDER_SERVICE.dto.OrderItemRequest;
import in.ms.ORDER_SERVICE.dto.OrderRequest;
import in.ms.ORDER_SERVICE.dto.ProductDto;
import in.ms.ORDER_SERVICE.dto.UserDto;
import in.ms.ORDER_SERVICE.entity.Order;
import in.ms.ORDER_SERVICE.entity.OrderItem;
import in.ms.ORDER_SERVICE.exception.ResourceNotFoundException;
import in.ms.ORDER_SERVICE.feign.ProductClient;
import in.ms.ORDER_SERVICE.feign.UserClient;
import in.ms.ORDER_SERVICE.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final UserClient userClient;




    @Override
    public Order createOrder(OrderRequest request) {

        UserDto user = userClient.getUser(request.getUserId());

        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setUserName(user.getName());


        List<OrderItem> orderItems = new ArrayList<>();
        double total = 0;

        for (OrderItemRequest item : request.getItems()) {

            ProductDto product = productClient.getProduct(item.getProductId());

            if (product == null) {
                throw new ResourceNotFoundException("Product not found");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName()); // ✅ FIX
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItem.setOrder(order);

            total += product.getPrice() * item.getQuantity();
            orderItems.add(orderItem);
        }

        order.setItems(orderItems);
        order.setTotalAmount(total);

        return orderRepository.save(order);
    }
//    @Override
//    public Order createOrder(OrderRequest request) {
//
//
//        UserDto user = userClient.getUser(request.getUserId());
//        if (user == null) {
//            throw new ResourceNotFoundException("User not found");
//        }
//
//        Order order = new Order();
//        order.setUserId(request.getUserId());
//
//        List<OrderItem> orderItems = new ArrayList<>();
//        double total = 0;
//
//        for (OrderItemRequest item : request.getItems()) {
//
//            // ✅ Get Product
//            ProductDto product = productClient.getProduct(item.getProductId());
//
//            if (product == null) {
//                throw new ResourceNotFoundException("Product not found");
//            }
//
//            OrderItem orderItem = new OrderItem();
//            orderItem.setProductId(product.getId());
//            orderItem.setQuantity(item.getQuantity());
//            orderItem.setPrice(product.getPrice());
//            orderItem.setOrder(order);
//
//            total += product.getPrice() * item.getQuantity();
//            orderItems.add(orderItem);
//        }
//
//        order.setItems(orderItems);
//        order.setTotalAmount(total);
//
//        return orderRepository.save(order);
//    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}