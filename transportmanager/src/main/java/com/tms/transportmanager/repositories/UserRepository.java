package com.tms.transportmanager.repositories;

import com.tms.transportmanager.entities.Station;
import com.tms.transportmanager.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User,String> {
    @Query("{role:?0,stationId:?1}")
    public List<User> findByRoleAndStationId(User.Role role, String stationId);
}
