package com.oms.ordermanager.repositories;

import com.oms.ordermanager.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order,String > {
    List<Order> findByCustomerId(String customerId);
}
