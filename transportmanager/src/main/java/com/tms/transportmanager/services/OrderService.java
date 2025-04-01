package com.tms.transportmanager.services;

import com.tms.transportmanager.entities.Order;
import com.tms.transportmanager.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private InventoryCallService inventoryCallService;
    @Autowired
    private PathFindingService pathFindingService;
    @Autowired
    private OrderRepository orderRepository;
    public Order createOrder(Order order){
        Map<String, Integer> skuQuantity = new HashMap<>();
        order.getItems().forEach((o)->skuQuantity.put(o.getSku(),o.getQuantity()));
        order.setAllocatedWarehouse(inventoryCallService.allocateWarehouse(skuQuantity,order.getLatitude(),order.getLongitude()));
        order.setStatus("Warehouse allocated : "+order.getAllocatedWarehouse().getWarehouseAddress());
        order.setPath(pathFindingService.findShortestPath(order.getAllocatedWarehouse().getLatitude(),order.getAllocatedWarehouse().getLongitude(),order.getLatitude(),order.getLongitude()));
        return orderRepository.save(order);
    }
    public Map<String,String> updateStatus(){
        List<Order> orders = orderRepository.findAll();
        Map<String,String> statuses = new HashMap<>();
        orders.forEach((o)->statuses.put(o.getId(),o.getStatus()));
        return statuses;
    }
    public String cancelOrder(String id){
        Optional<Order> order = orderRepository.findById(id);
        if(order.isEmpty())
            throw new RuntimeException("Order not found");
        order.get().setStatus("Cancelled");
        orderRepository.save(order.get());
        return "Order successfully cancelled";
    }
}
