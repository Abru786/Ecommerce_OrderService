package in.ms.ORDER_SERVICE.service;

import in.ms.ORDER_SERVICE.dto.OrderRequest;
import in.ms.ORDER_SERVICE.entity.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequest request);

   Order  getOrderById(Long id);

   List<Order> getAllOrders();

    List<Order> getOrdersByUser(Long userId);
}
