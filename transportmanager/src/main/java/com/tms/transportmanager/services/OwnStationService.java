package com.tms.transportmanager.services;

import com.tms.transportmanager.entities.Delivery;
import com.tms.transportmanager.entities.Order;
import com.tms.transportmanager.entities.Station;
import com.tms.transportmanager.entities.User;
import com.tms.transportmanager.repositories.OrderRepository;
import com.tms.transportmanager.repositories.StationRepository;
import com.tms.transportmanager.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OwnStationService {
    @Autowired
    private StationRepository stationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Scheduled(fixedRate = 5000)
    public void assignDelPartWarehouse(){
        Optional<Order> order = orderRepository.findByNotTakenFromWarehouseAndFirstIndex().stream().findFirst();
        if(order.isEmpty()){
            return;
        }
        Station station = order.get().getPath().get(0);
        Optional<User> deliveryAgent = userRepository.findByRoleAndStationId(User.Role.DELIVERY_AGENT,station.getId()).stream().filter((u)->u.getDeliveries().size()<5).findFirst();
        if(deliveryAgent.isEmpty())
            return;
        order.get().setTakenfromWarehouse(true);
        Delivery delivery = new Delivery(order.get().getId(),order.get().getAllocatedWarehouse().getWarehouseAddress(),station.getAddress(),false);
        deliveryAgent.get().getDeliveries().add(delivery);
        userRepository.save(deliveryAgent.get());
        orderRepository.save(order.get());
    }
    public List<Order> getOrders(String stationId){
        Optional<Station> station = stationRepository.findById(stationId);
        return station.map(Station::getOrders).orElse(null);
    }
    public String deliverOrder(String orderId,String stationId){
        Optional<Station> station = stationRepository.findById(stationId);
        Optional<User> deliveryAgent = station.get().getDeliveryAgents().stream().filter((u)->u.getDeliveries().size()<5).findFirst();
        Optional<Order> order = orderRepository.findById(orderId);
        Delivery delivery;
        String res;
        if(order.get().getCurrentStationIndex()<order.get().getPath().size()-1) {
            Station nextStation = order.get().getPath().get(order.get().getCurrentStationIndex() + 1);
            delivery = new Delivery(orderId,station.get().getAddress(),nextStation.getAddress(),true);
            res="Order shipping to station"+station.get().getAddress();
        }
        else {
            delivery = new Delivery(orderId,station.get().getAddress(),order.get().getCustomerAddress(),true);
            res="Order out for delivery";
        }
        deliveryAgent.get().getDeliveries().add(delivery);
        order.get().setStatus(res);
        userRepository.save(deliveryAgent.get());
        orderRepository.save(order.get());
        return res;
    }
    public String createAgent(User user,String stationId){
        user.setStationId(stationId);
        user.setRole(User.Role.DELIVERY_AGENT);
        user.setDeliveries(new ArrayList<>());
        Optional<Station> station = stationRepository.findById(stationId);
        station.get().getDeliveryAgents().add(user);
        userRepository.save(user);
        stationRepository.save(station.get());
        return "Agent Created Successfully";
    }
    public List<User> getAgents(String stationId){
        Optional<Station> station = stationRepository.findById(stationId);
        return station.get().getDeliveryAgents();
    }
}
