package com.dms.supplymanager.repositories;

import com.dms.supplymanager.entities.Warehouse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends MongoRepository<Warehouse, String> {
    @Query("{ 'warehouseProducts': { $elemMatch: { 'sku': ?0, 'quantity': { $gt: ?1 } } } }")
    List<Warehouse> findByProductWithMinQuantity(String sku, Long minQuantity);

    @Query(value = "{ 'warehouseProducts': { $elemMatch: { 'sku': ?0, 'quantity': { $gt: ?1 } } } }",
            exists = true)
    boolean existsByProductWithMinQuantity(String sku, Long minQuantity);
}
