package in.ms.ORDER_SERVICE.dto;


import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Long userId;
    private String userName;
    private List<OrderItemRequest> items;
}

