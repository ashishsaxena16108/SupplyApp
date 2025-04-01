package com.dms.supplymanager.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Document(collection = "warehouses")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Warehouse {
    @Id
    private String id;
    private String warehouseAddress;
    private Double latitude;
    private Double longitude;
    private String state;
    private String city;
    private String country;
    @DBRef
    private List<WarehouseProducts> warehouseProducts;

}
