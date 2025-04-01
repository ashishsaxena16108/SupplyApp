package com.dms.supplymanager.controllers;

import com.dms.supplymanager.entities.WarehouseProducts;
import com.dms.supplymanager.repositories.WarehouseRepository;
import com.dms.supplymanager.services.DistanceCalculator;
import com.dms.supplymanager.entities.Products;
import com.dms.supplymanager.entities.Warehouse;
import com.dms.supplymanager.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private WarehouseRepository warehouseRepository;

    @GetMapping("/check")
    public ResponseEntity<?> checkInventory(@RequestParam("sku")String sku,@RequestParam("quantity")Long quantity){
         boolean existsProduct = warehouseRepository.existsByProductWithMinQuantity(sku,quantity);
         if(existsProduct)
             return ResponseEntity.ok("Product is available");
         return ResponseEntity.badRequest().body("Product is not available");
    }
    @PostMapping("/check-nearest")
    public ResponseEntity<?> checkNearestWarehouse(
            Map<String, Integer> skuQuantity,
            @RequestParam("lat") Double latitude,
            @RequestParam("long") Double longitude) {

        try {
            List<Warehouse> warehouses = warehouseRepository.findAll(); // Get all warehouses

            // Filter warehouses that have all required products with sufficient stock
            List<Warehouse> validWarehouses = warehouses.stream()
                    .filter(warehouse -> hasAllProductsWithRequiredStock(warehouse, skuQuantity))
                    .collect(Collectors.toList());

            if (validWarehouses.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("❌ No warehouse found with enough stock for all requested products.");
            }

            // Find the nearest warehouse from the filtered list
            Warehouse nearestWarehouse = validWarehouses.stream()
                    .min(Comparator.comparingDouble(warehouse ->
                            DistanceCalculator.calculateDistance(
                                    latitude, longitude,
                                    warehouse.getLatitude(), warehouse.getLongitude())))
                    .orElseThrow(() -> new RuntimeException("❌ No nearest warehouse found."));

            return ResponseEntity.ok(nearestWarehouse);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Error: " + e.getMessage());
        }
    }

    /**
     * Helper method to check if the warehouse has all products with required quantities
     */
    private boolean hasAllProductsWithRequiredStock(Warehouse warehouse, Map<String, Integer> skuQuantity) {
        for (Map.Entry<String, Integer> entry : skuQuantity.entrySet()) {
            String sku = entry.getKey();
            int requiredQuantity = entry.getValue();

            Optional<WarehouseProducts> stock = warehouse.getWarehouseProducts().stream()
                    .filter(p -> p.getSku().equals(sku) && p.getQuantity() >= requiredQuantity)
                    .findFirst();

            if (stock.isEmpty()) {
                return false; // If any product is missing or insufficient, reject this warehouse
            }
        }
        return true; // Warehouse has all required products with enough quantity
    }
}
