package com.tms.transportmanager.repositories;

import com.tms.transportmanager.entities.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends MongoRepository<Order,String> {
   @Query("{takenFromWarehouse:false,currentStationIndex:0}")
    List<Order> findByNotTakenFromWarehouseAndFirstIndex();
}
