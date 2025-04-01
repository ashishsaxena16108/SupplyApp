package com.tms.transportmanager.controllers;

import com.tms.transportmanager.entities.Delivery;
import com.tms.transportmanager.repositories.OrderRepository;
import com.tms.transportmanager.services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {
    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/{delivery-id}")
    public ResponseEntity<List<Delivery>> getDeliveries(@PathVariable("delivery-id") String deliveryId){
        return ResponseEntity.ok(deliveryService.getDeliveries(deliveryId));
    }
    @GetMapping("/{delivery-id}/{order-id}")
    public ResponseEntity<String> deliverOrder(@PathVariable("delivery-id") String deliveryId,@PathVariable("order-id")String orderId){
        return ResponseEntity.ok(deliveryService.orderDelivered(orderId,deliveryId));
    }
}
