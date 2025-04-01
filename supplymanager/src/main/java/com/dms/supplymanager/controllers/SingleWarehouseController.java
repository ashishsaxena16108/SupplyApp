package com.dms.supplymanager.controllers;

import com.dms.supplymanager.entities.Products;
import com.dms.supplymanager.entities.Warehouse;
import com.dms.supplymanager.entities.WarehouseProducts;
import com.dms.supplymanager.repositories.ProductRepository;
import com.dms.supplymanager.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/warehouse/{id}")
public class SingleWarehouseController {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @Autowired
    private ProductRepository productRepository;
    @GetMapping("/")
    public ResponseEntity<?> getOwnWarehouseDetails(@PathVariable String id){
        Optional<Warehouse> existingwarehouse = warehouseRepository.findById(id);
        Map<String,String> map = new HashMap<>();
        if(existingwarehouse.isEmpty()) {
            map.put("error","Warehouse not existed");
            return ResponseEntity.badRequest().body(map);
        }
        return ResponseEntity.ok(existingwarehouse.get());
    }
    @GetMapping("/products")
    public ResponseEntity<List<Products>> getProducts(@PathVariable String id){
        Optional<Warehouse> existingwarehouse = warehouseRepository.findById(id);
        return existingwarehouse.map(warehouse -> ResponseEntity.ok(warehouse.getWarehouseProducts().stream().map(x->productRepository.findBySku(x.getSku())).toList())).orElseGet(() -> ResponseEntity.badRequest().body(null));
    }
    @PostMapping("/add-product")
    public ResponseEntity<?> addProductToList(@RequestParam("quantity")Long quantity,@RequestParam("sku")String sku,@PathVariable String id){
        Optional<Warehouse> existingwarehouse = warehouseRepository.findById(id);
        if(existingwarehouse.isEmpty())
            return ResponseEntity.badRequest().body("Warehouse Not Found");
        WarehouseProducts warehouseProduct = new WarehouseProducts(sku,id,quantity);
        existingwarehouse.get().getWarehouseProducts().add(warehouseProduct);
        warehouseRepository.save(existingwarehouse.get());
        return ResponseEntity.ok("Product successfully added");
    }
}
