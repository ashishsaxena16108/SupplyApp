package com.tms.transportmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Delivery {
    private String orderId;
    private String from;
    private String to;
    private Boolean send;
}
