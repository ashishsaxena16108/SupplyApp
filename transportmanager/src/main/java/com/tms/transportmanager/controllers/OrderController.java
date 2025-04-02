package com.tms.transportmanager.controllers;

import com.tms.transportmanager.entities.Order;
import com.tms.transportmanager.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
   @Autowired
   private OrderService orderService;
   @PostMapping("/create")
   public ResponseEntity<?> createOrder(@RequestBody Order order){
      Order order1 = orderService.createOrder(order);
      return ResponseEntity.ok("Order created");
   }
   @GetMapping("/update-status")
   public ResponseEntity<?> updateStatus(){
      return ResponseEntity.ok(orderService.updateStatus());
   }
   @DeleteMapping("/cancel-order")
   public ResponseEntity<?> cancelOrder(@RequestParam String id){
      return ResponseEntity.ok(orderService.cancelOrder(id));
   }
}
