package com.oms.ordermanager.services;

import com.oms.ordermanager.dtos.OrderItemDTO;
import com.oms.ordermanager.dtos.OrderRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderValidationService {

    private final InventoryService inventoryService;

    public void validateOrder(OrderRequestDTO order) {
        for (OrderItemDTO item : order.getOrderItems()) {
            if (!inventoryService.isProductAvailable(item.getSku(),item.getQuantity())) {
                throw new IllegalArgumentException("Product " + item.getSku() + " is out of stock.");
            }
        }
    }

}

