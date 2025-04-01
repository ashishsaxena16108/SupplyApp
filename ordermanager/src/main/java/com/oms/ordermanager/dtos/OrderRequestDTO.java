package com.oms.ordermanager.dtos;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {
    @NotNull(message = "Customer ID is required")
    private String customerId;

    @NotEmpty(message = "Order items cannot be empty")
    @Size(min = 1, message = "At least one item is required")
    private List<@Valid OrderItemDTO> orderItems;

    private Double totalPrice;
    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;
    private String paymentId;
}

