package com.tms.transportmanager.services;

import com.tms.transportmanager.entities.Warehouse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class InventoryCallService {
    private final RestTemplate restTemplate = new RestTemplate();
    public Warehouse allocateWarehouse(Map<String,Integer> skuQuantity, Double latitude, Double longitude){
        String inventoryUrl = "http://localhost:8082/inventory/check-nearest?lat="+latitude+"&long="+longitude;
        try {
            Warehouse warehouse = restTemplate.postForObject(inventoryUrl, skuQuantity, Warehouse.class);
            return warehouse;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
