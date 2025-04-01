package com.dms.supplymanager.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "warehouse-products")
@AllArgsConstructor
public class WarehouseProducts {
    private String sku;
    private String warehouseId;
    private Long quantity;
}
