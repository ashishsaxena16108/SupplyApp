package com.dms.supplymanager.controllers;

import com.dms.supplymanager.entities.Warehouse;
import com.dms.supplymanager.repositories.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/warehouse")
public class WarehouseController {
    @Autowired
    private WarehouseRepository warehouseRepository;
    @GetMapping("/all")
    public ResponseEntity<List<Warehouse>> getAllWarehousesDetails(){
        return ResponseEntity.ok(warehouseRepository.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getWarehouseDetails(@PathVariable String id){
        Optional<Warehouse> warehouse = warehouseRepository.findById(id);
        if(warehouse.isEmpty())
            return ResponseEntity.badRequest().body("Warehouse not existed");
        return ResponseEntity.ok(warehouse.get());
    }
    @PostMapping("/add")
    public ResponseEntity<?> addNewWarehouse(@RequestBody Warehouse warehouse) {
        Warehouse warehouse1;
        try {
            warehouse1 = warehouseRepository.save(warehouse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Some error occurred");
        }
        return ResponseEntity.ok(warehouse1);
    }
    @PostMapping("/edit/{id}")
    public ResponseEntity<?> editWarehouse(@PathVariable String id,@RequestBody Warehouse warehouse){
        Optional<Warehouse> existedWarehouse = warehouseRepository.findById(id);
        if(existedWarehouse.isEmpty())
            return ResponseEntity.badRequest().body("Warehouse not existed");
        existedWarehouse.get().setCity(warehouse.getCity());
        existedWarehouse.get().setWarehouseAddress(warehouse.getWarehouseAddress());
        existedWarehouse.get().setCountry(warehouse.getCountry());
        existedWarehouse.get().setState(warehouse.getState());
        existedWarehouse.get().setLongitude(warehouse.getLongitude());
        existedWarehouse.get().setLatitude(warehouse.getLatitude());
        warehouseRepository.save(existedWarehouse.get());
        return ResponseEntity.ok(existedWarehouse.get());
    }
}
