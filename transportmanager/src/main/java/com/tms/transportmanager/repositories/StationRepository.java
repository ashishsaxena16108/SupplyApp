package com.tms.transportmanager.repositories;

import com.tms.transportmanager.entities.Station;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends MongoRepository<Station,String> {

}
