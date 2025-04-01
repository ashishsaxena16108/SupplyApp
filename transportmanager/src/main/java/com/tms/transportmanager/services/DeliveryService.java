package com.tms.transportmanager.services;

import com.tms.transportmanager.entities.Delivery;
import com.tms.transportmanager.entities.Order;
import com.tms.transportmanager.entities.Station;
import com.tms.transportmanager.entities.User;
import com.tms.transportmanager.repositories.OrderRepository;
import com.tms.transportmanager.repositories.StationRepository;
import com.tms.transportmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeliveryService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StationRepository stationRepository;

    public List<Delivery> getDeliveries(String userId){
        Optional<User> deliveryAgent = userRepository.findById(userId);
        return deliveryAgent.map(User::getDeliveries).orElse(null);
    }

    public String orderDelivered(String orderId,String userId){
        Optional<User> deliveryAgent = userRepository.findById(userId);
        Optional<Order> order = orderRepository.findById(orderId);
        Station prevStation = order.get().getPath().get(order.get().getCurrentStationIndex());
        Station nextStation = order.get().getPath().get(order.get().getCurrentStationIndex()+1);
        prevStation.getOrders().remove(order.get());
        nextStation.getOrders().add(order.get());
        deliveryAgent.get().setDeliveries(deliveryAgent.get().getDeliveries().stream().filter((d)->!d.getOrderId().equals(orderId)).toList());
        order.get().setCurrentStationIndex(order.get().getCurrentStationIndex()+1);
        order.get().setStatus(order.get().getCurrentStationIndex()==order.get().getPath().size()?"Order delivered":"Order reached to station "+nextStation.getAddress());
        orderRepository.save(order.get());
        userRepository.save(deliveryAgent.get());
        stationRepository.save(prevStation);
        stationRepository.save(nextStation);
        return order.get().getStatus();
    }
}
