package com.oms.ordermanager.entities;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Document(collection = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    private String Id;
    private String customerId;
    private String customerAddress;
    private Double latitude;
    private Double longitude;
    private List<OrderItem> items;
    private double totalPrice;
    private String status;
}
