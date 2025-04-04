package com.tms.transportmanager.controllers;

import com.tms.transportmanager.entities.Station;
import com.tms.transportmanager.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/station")
public class StationController {
   @Autowired
   private StationService stationService;

   @PostMapping("/create")
   public ResponseEntity<?> createStation(@RequestBody Station station){
       Station station1 = stationService.createStation(station);
       if(station==null)
          return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Station not created");
       return ResponseEntity.status(HttpStatus.CREATED).body(station1);
   }
   @PostMapping("/create-all")
   public ResponseEntity<?> createAllStation(@RequestBody List<Station> stations){
       stationService.createAllStations(stations);
       return ResponseEntity.status(HttpStatus.CREATED).body("All stations created");
   }
}
