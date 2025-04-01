package com.oms.ordermanager.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class InventoryService {
    private final RestTemplate restTemplate = new RestTemplate();

    public boolean isProductAvailable(String sku,Integer quantity) {
         String inventoryApiUrl = "http://localhost:8082/inventory/check?sku="+sku+"&quantity="+quantity;

        ResponseEntity<String> response = restTemplate.getForEntity(
                inventoryApiUrl, String.class
        );
        return !response.getStatusCode().is4xxClientError();
    }

}
