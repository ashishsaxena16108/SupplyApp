package com.tms.transportmanager.services;

import com.tms.transportmanager.entities.Station;
import com.tms.transportmanager.repositories.StationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {
    private static final Logger log = LoggerFactory.getLogger(StationService.class);
    @Autowired
    private StationRepository stationRepository;
    public Station createStation(Station station) {
        try {
            List<Station> stations = stationRepository.findAll();

            // Find nearby stations
            List<Station> nearbyStations = stations.stream()
                    .filter(s -> !s.getId().equals(station.getId()) && DistanceCalculator.calculateDistance(
                            station.getLatitude(), station.getLongitude(),
                            s.getLatitude(), s.getLongitude()) <= 1000)
                    .collect(Collectors.toList());

            // Save the station first (WITHOUT neighbors to break recursion)
            station.setNeighborIds(new ArrayList<>()); // Store only IDs
            Station createdStation = stationRepository.save(station);

            // Now update neighbors (storing only neighbor IDs)
            List<String> neighborIds = nearbyStations.stream()
                    .map(Station::getId)
                    .collect(Collectors.toList());

            createdStation.setNeighborIds(neighborIds);
            stationRepository.save(createdStation);

            // Update existing nearby stations to include the new station ID
            for (Station s : nearbyStations) {
                if (!s.getId().equals(station.getId()) && !s.getNeighborIds().contains(createdStation.getId())) {
                    s.getNeighborIds().add(createdStation.getId());
                }
            }

            // Save updated nearby stations
            stationRepository.saveAll(nearbyStations);

            return createdStation;

        } catch (Exception e) {
            log.error("Error in createStation: ", e);
            return null;
        }
    }

    public String createAllStations(List<Station> stations){
        stations.forEach((s)->s.setDeliveryAgents(new ArrayList<>()));
        stationRepository.saveAll(stations);
        return "Created";
    }
}
