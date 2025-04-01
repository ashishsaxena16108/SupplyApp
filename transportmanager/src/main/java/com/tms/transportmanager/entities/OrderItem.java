package com.tms.transportmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderItem {
    @Id
    private String Id;
    private String orderId;
    private String sku;
    private String productName;
    private int quantity;
    private double price;
    private double weight;
    private double height;
    private double width;
    private double length;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
