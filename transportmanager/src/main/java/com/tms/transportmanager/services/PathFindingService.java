package com.tms.transportmanager.services;


import com.tms.transportmanager.entities.Station;
import com.tms.transportmanager.repositories.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PathFindingService {

    @Autowired
    private StationRepository stationRepository;

    public List<Station> findShortestPath(Double warehouseLat, Double warehouseLong, Double lat, Double lon) {
        List<Station> stations = stationRepository.findAll();

        // Step 1: Find nearest station to warehouse
        Station startStation = findNearestStation(warehouseLat, warehouseLong, stations);

        // Step 2: Find nearest station to delivery address
        Station endStation = findNearestStation(lat, lon, stations);

        if (startStation == null || endStation == null) {
            throw new RuntimeException("❌ No nearby stations found.");
        }

        return dijkstraShortestPath(startStation, endStation);
    }

    private Station findNearestStation(Double latitude, Double longitude, List<Station> stations) {
        return stations.stream()
                .min(Comparator.comparingDouble(station ->
                        DistanceCalculator.calculateDistance(latitude, longitude,
                                station.getLatitude(), station.getLongitude())))
                .orElse(null);
    }

    private List<Station> dijkstraShortestPath(Station start, Station end) {
        Map<Station, Double> distances = new HashMap<>();
        Map<Station, Station> previousStations = new HashMap<>();
        PriorityQueue<Station> queue = new PriorityQueue<>(Comparator.comparing(distances::get));

        for (Station station : stationRepository.findAll()) {
            distances.put(station, Double.MAX_VALUE);
            previousStations.put(station, null);
        }

        distances.put(start, 0.0);
        queue.add(start);

        while (!queue.isEmpty()) {
            Station current = queue.poll();

            if (current.equals(end)) break;

            for (Station neighbor : current.getNeighbors()) {
                double newDist = distances.get(current) + DistanceCalculator.calculateDistance(current.getLatitude(),current.getLongitude(),neighbor.getLatitude(),neighbor.getLongitude());

                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previousStations.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        return reconstructPath(previousStations, end);
    }

    private List<Station> reconstructPath(Map<Station, Station> previousStations, Station end) {
        List<Station> path = new LinkedList<>();
        for (Station at = end; at != null; at = previousStations.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}
