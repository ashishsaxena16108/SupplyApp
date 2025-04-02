package com.tms.transportmanager.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Warehouse {
    private String warehouseAddress;
    private Double latitude;
    private Double longitude;

}
