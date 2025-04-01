package com.tms.transportmanager.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class Station {
    private String id;
    private String city;
    private String state;
    private Double latitude;
    private Double longitude;
    private String address;
    private List<Order> orders;
    @DBRef
    private List<User> DeliveryAgents;
    @DBRef
    private List<Station> neighbors;
}
