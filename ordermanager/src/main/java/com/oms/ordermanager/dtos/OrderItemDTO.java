package com.oms.ordermanager.dtos;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OrderItemDTO {

    @NotBlank(message = "SKU is required")
    private String sku;
    private String productName;
    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;
    private double weight;
    private double height;
    private double width;
    private double length;
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    private Double price;
}

