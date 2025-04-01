package com.dms.supplymanager.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "products")
@Data
public class Products {
    @Id
    private String id;
    private String productName;
    private String sku;
    private double price;
    private double weight;
    private double height;
    private double width;
    private double length;
}
