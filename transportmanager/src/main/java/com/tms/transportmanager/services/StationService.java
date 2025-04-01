package com.tms.transportmanager.services;

import com.tms.transportmanager.entities.Station;
import com.tms.transportmanager.repositories.StationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StationService {
    private static final Logger log = LoggerFactory.getLogger(StationService.class);
    @Autowired
    private StationRepository stationRepository;
    public Station createStation(Station station){
        try{
            List<Station> stations = stationRepository.findAll();
            List<Station> nearbyStations = stations.stream()
                    .filter(s -> DistanceCalculator.calculateDistance(
                            station.getLatitude(), station.getLongitude(),
                            s.getLatitude(), s.getLongitude()) <= 1000)
                    .collect(Collectors.toList());
            nearbyStations.forEach((s)-> {s.getNeighbors().add(station); stationRepository.save(s);});
            station.setNeighbors(nearbyStations);
            return stationRepository.save(station);
        } catch (Exception e) {
            log.error("e: ", e);
            return null;
        }
    }

}
