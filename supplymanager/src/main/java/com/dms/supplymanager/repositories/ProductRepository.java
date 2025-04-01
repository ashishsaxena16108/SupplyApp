package com.dms.supplymanager.repositories;

import com.dms.supplymanager.entities.Products;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Products,String> {
    @Query("{sku:?0}")
    public Products findBySku(String sku);
}
