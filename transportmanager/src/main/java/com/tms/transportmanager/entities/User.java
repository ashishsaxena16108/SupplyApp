package com.tms.transportmanager.entities;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@Data
public class User {
    private String id;
    private String name;
    private Integer phoneNumber;
    private String email;
    private String password;
    private String stationId;
    private Role role;
    private List<Delivery> deliveries;
    public enum Role{
        ADMIN,INSPECTOR,DELIVERY_AGENT
    }
}
