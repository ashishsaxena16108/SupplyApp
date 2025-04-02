package com.tms.transportmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@AllArgsConstructor
public class User {
    private String id;
    private String name;
    private Long phoneNumber;
    private String email;
    private String password;
    private String stationId;
    private Role role;
    private List<Delivery> deliveries = new ArrayList<>();
    public enum Role{
        ADMIN,INSPECTOR,DELIVERY_AGENT
    }
}
