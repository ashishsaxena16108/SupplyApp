package com.tms.transportmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "stations")
@Data
@AllArgsConstructor
public class Station {
    private String id;
    private String city;
    private String state;
    private Double latitude;
    private Double longitude;
    private String address;
    @DBRef
    private List<Order> orders = new ArrayList<>();
    @DBRef
    private List<User> DeliveryAgents = new ArrayList<>();
    private List<String> neighborIds = new ArrayList<>();
}
