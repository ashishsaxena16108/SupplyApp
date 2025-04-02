package com.tms.transportmanager.controllers;

import com.tms.transportmanager.entities.Order;
import com.tms.transportmanager.entities.User;
import com.tms.transportmanager.services.OwnStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/station/{stationId}")
public class OwnStationController {
    @Autowired
    private OwnStationService ownStationService;
    @GetMapping("")
    public List<Order> getOrders(@PathVariable String stationId){
        return ownStationService.getOrders(stationId);
    }
    @GetMapping("/{orderId}")
    public String assignDeliveryPartner(@PathVariable String stationId,@PathVariable String orderId){
        return ownStationService.deliverOrder(orderId,stationId);
    }
    @PostMapping("/create-agent")
    public ResponseEntity<String> createAgent(@RequestBody User user, @PathVariable String stationId){
        return ResponseEntity.status(HttpStatus.CREATED).body(ownStationService.createAgent(user,stationId));
    }
    @GetMapping("/delivery-agents")
    public ResponseEntity<List<User>> getAllAgents(@PathVariable String stationId){
        return ResponseEntity.ok(ownStationService.getAgents(stationId));
    }
}
