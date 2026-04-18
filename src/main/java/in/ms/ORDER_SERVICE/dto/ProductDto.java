package in.ms.ORDER_SERVICE.dto;


import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private Double price;
}