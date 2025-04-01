package com.tms.transportmanager.controllers;

import com.tms.transportmanager.entities.Order;
import com.tms.transportmanager.services.OwnStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
